(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolePermisoDialogController', RolePermisoDialogController);

    RolePermisoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RolePermiso', 'Permiso', 'Rol'];

    function RolePermisoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RolePermiso, Permiso, Rol) {
        var vm = this;

        vm.rolePermiso = entity;
        vm.clear = clear;
        vm.save = save;
        vm.permisos = Permiso.query();
        vm.rols = Rol.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rolePermiso.id !== null) {
                RolePermiso.update(vm.rolePermiso, onSaveSuccess, onSaveError);
            } else {
                RolePermiso.save(vm.rolePermiso, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:rolePermisoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
