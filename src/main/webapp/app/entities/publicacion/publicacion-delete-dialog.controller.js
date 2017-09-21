(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionDeleteController',PublicacionDeleteController);

    PublicacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Publicacion'];

    function PublicacionDeleteController($uibModalInstance, entity, Publicacion) {
        var vm = this;

        vm.publicacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Publicacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
