(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('BibliotecaController', BibliotecaController);

    BibliotecaController.$inject = ['Biblioteca'];

    function BibliotecaController(Biblioteca) {

        var vm = this;

        vm.bibliotecas = [];

        loadAll();

        function loadAll() {
            Biblioteca.query(function(result) {
                vm.bibliotecas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
