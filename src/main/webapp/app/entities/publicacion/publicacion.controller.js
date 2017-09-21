(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionController', PublicacionController);

    PublicacionController.$inject = ['DataUtils', 'Publicacion'];

    function PublicacionController(DataUtils, Publicacion) {

        var vm = this;

        vm.publicacions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Publicacion.query(function(result) {
                vm.publicacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
