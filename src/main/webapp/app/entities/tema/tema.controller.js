(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('TemaController', TemaController);

    TemaController.$inject = ['Tema'];

    function TemaController(Tema) {

        var vm = this;

        vm.temas = [];

        loadAll();

        function loadAll() {
            Tema.query(function(result) {
                vm.temas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
