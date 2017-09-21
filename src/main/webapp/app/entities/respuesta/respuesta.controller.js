(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RespuestaController', RespuestaController);

    RespuestaController.$inject = ['Respuesta'];

    function RespuestaController(Respuesta) {

        var vm = this;

        vm.respuestas = [];

        loadAll();

        function loadAll() {
            Respuesta.query(function(result) {
                vm.respuestas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
