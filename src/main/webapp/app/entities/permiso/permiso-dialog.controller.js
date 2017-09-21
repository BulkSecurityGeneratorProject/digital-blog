(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PermisoDialogController', PermisoDialogController);

    PermisoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Permiso'];

    function PermisoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Permiso) {
        var vm = this;

        vm.permiso = entity;
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
            if (vm.permiso.id !== null) {
                Permiso.update(vm.permiso, onSaveSuccess, onSaveError);
            } else {
                Permiso.save(vm.permiso, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:permisoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
