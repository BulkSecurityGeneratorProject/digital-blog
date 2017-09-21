(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CapituloController', CapituloController);

    CapituloController.$inject = ['Capitulo'];

    function CapituloController(Capitulo) {

        var vm = this;

        vm.capitulos = [];

        loadAll();

        function loadAll() {
            Capitulo.query(function(result) {
                vm.capitulos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
