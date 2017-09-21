(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ImagenPorPublicacionController', ImagenPorPublicacionController);

    ImagenPorPublicacionController.$inject = ['DataUtils', 'ImagenPorPublicacion'];

    function ImagenPorPublicacionController(DataUtils, ImagenPorPublicacion) {

        var vm = this;

        vm.imagenPorPublicacions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            ImagenPorPublicacion.query(function(result) {
                vm.imagenPorPublicacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
