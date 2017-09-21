(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolePermisoController', RolePermisoController);

    RolePermisoController.$inject = ['RolePermiso'];

    function RolePermisoController(RolePermiso) {

        var vm = this;

        vm.rolePermisos = [];

        loadAll();

        function loadAll() {
            RolePermiso.query(function(result) {
                vm.rolePermisos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
