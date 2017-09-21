(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionPublicacionController', SeccionPublicacionController);

    SeccionPublicacionController.$inject = ['SeccionPublicacion'];

    function SeccionPublicacionController(SeccionPublicacion) {

        var vm = this;

        vm.seccionPublicacions = [];

        loadAll();

        function loadAll() {
            SeccionPublicacion.query(function(result) {
                vm.seccionPublicacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
