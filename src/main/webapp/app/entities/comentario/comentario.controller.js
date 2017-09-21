(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ComentarioController', ComentarioController);

    ComentarioController.$inject = ['Comentario'];

    function ComentarioController(Comentario) {

        var vm = this;

        vm.comentarios = [];

        loadAll();

        function loadAll() {
            Comentario.query(function(result) {
                vm.comentarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
