(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PaginaController', PaginaController);

    PaginaController.$inject = ['Pagina'];

    function PaginaController(Pagina) {

        var vm = this;

        vm.paginas = [];

        loadAll();

        function loadAll() {
            Pagina.query(function(result) {
                vm.paginas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
