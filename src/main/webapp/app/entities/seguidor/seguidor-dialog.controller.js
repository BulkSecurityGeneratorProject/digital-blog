(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeguidorDialogController', SeguidorDialogController);

    SeguidorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Seguidor', 'Usuario'];

    function SeguidorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Seguidor, Usuario) {
        var vm = this;

        vm.seguidor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seguidor.id !== null) {
                Seguidor.update(vm.seguidor, onSaveSuccess, onSaveError);
            } else {
                Seguidor.save(vm.seguidor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:seguidorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
