(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotaController', NotaController);

    NotaController.$inject = ['Nota'];

    function NotaController(Nota) {

        var vm = this;

        vm.notas = [];

        loadAll();

        function loadAll() {
            Nota.query(function(result) {
                vm.notas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
