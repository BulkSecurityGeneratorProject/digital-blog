(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CategoriaController', CategoriaController);

    CategoriaController.$inject = ['Categoria'];

    function CategoriaController(Categoria) {

        var vm = this;

        vm.categorias = [];

        loadAll();

        function loadAll() {
            Categoria.query(function(result) {
                vm.categorias = result;
                vm.searchQuery = null;
            });
        }
    }
})();
