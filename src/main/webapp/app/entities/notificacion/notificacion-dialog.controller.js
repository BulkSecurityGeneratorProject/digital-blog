(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotificacionDialogController', NotificacionDialogController);

    NotificacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Notificacion'];

    function NotificacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Notificacion) {
        var vm = this;

        vm.notificacion = entity;
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
            if (vm.notificacion.id !== null) {
                Notificacion.update(vm.notificacion, onSaveSuccess, onSaveError);
            } else {
                Notificacion.save(vm.notificacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:notificacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
