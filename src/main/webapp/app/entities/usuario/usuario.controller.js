(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('UsuarioController', UsuarioController);

    UsuarioController.$inject = ['DataUtils', 'Usuario'];

    function UsuarioController(DataUtils, Usuario) {

        var vm = this;

        vm.usuarios = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Usuario.query(function(result) {
                vm.usuarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
