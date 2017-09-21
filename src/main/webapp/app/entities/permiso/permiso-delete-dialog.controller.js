(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PermisoDeleteController',PermisoDeleteController);

    PermisoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Permiso'];

    function PermisoDeleteController($uibModalInstance, entity, Permiso) {
        var vm = this;

        vm.permiso = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Permiso.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
