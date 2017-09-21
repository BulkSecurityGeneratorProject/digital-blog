(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ImagenPorPublicacionDeleteController',ImagenPorPublicacionDeleteController);

    ImagenPorPublicacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ImagenPorPublicacion'];

    function ImagenPorPublicacionDeleteController($uibModalInstance, entity, ImagenPorPublicacion) {
        var vm = this;

        vm.imagenPorPublicacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ImagenPorPublicacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
