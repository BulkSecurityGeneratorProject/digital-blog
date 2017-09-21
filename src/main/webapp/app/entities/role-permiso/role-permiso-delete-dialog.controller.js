(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolePermisoDeleteController',RolePermisoDeleteController);

    RolePermisoDeleteController.$inject = ['$uibModalInstance', 'entity', 'RolePermiso'];

    function RolePermisoDeleteController($uibModalInstance, entity, RolePermiso) {
        var vm = this;

        vm.rolePermiso = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RolePermiso.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
