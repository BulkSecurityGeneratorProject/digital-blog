(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolController', RolController);

    RolController.$inject = ['Rol'];

    function RolController(Rol) {

        var vm = this;

        vm.rols = [];

        loadAll();

        function loadAll() {
            Rol.query(function(result) {
                vm.rols = result;
                vm.searchQuery = null;
            });
        }
    }
})();
