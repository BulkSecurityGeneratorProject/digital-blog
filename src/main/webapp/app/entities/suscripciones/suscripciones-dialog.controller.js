(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SuscripcionesDialogController', SuscripcionesDialogController);

    SuscripcionesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Suscripciones'];

    function SuscripcionesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Suscripciones) {
        var vm = this;

        vm.suscripciones = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.suscripciones.id !== null) {
                Suscripciones.update(vm.suscripciones, onSaveSuccess, onSaveError);
            } else {
                Suscripciones.save(vm.suscripciones, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:suscripcionesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
