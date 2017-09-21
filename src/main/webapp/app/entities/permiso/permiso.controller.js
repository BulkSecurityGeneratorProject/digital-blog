(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PermisoController', PermisoController);

    PermisoController.$inject = ['Permiso'];

    function PermisoController(Permiso) {

        var vm = this;

        vm.permisos = [];

        loadAll();

        function loadAll() {
            Permiso.query(function(result) {
                vm.permisos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
