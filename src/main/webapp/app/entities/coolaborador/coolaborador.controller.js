(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CoolaboradorController', CoolaboradorController);

    CoolaboradorController.$inject = ['Coolaborador'];

    function CoolaboradorController(Coolaborador) {

        var vm = this;

        vm.coolaboradors = [];

        loadAll();

        function loadAll() {
            Coolaborador.query(function(result) {
                vm.coolaboradors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
