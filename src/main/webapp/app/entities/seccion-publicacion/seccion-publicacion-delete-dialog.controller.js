(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionPublicacionDeleteController',SeccionPublicacionDeleteController);

    SeccionPublicacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'SeccionPublicacion'];

    function SeccionPublicacionDeleteController($uibModalInstance, entity, SeccionPublicacion) {
        var vm = this;

        vm.seccionPublicacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SeccionPublicacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
