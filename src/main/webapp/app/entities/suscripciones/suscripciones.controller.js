(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SuscripcionesController', SuscripcionesController);

    SuscripcionesController.$inject = ['Suscripciones'];

    function SuscripcionesController(Suscripciones) {

        var vm = this;

        vm.suscripciones = [];

        loadAll();

        function loadAll() {
            Suscripciones.query(function(result) {
                vm.suscripciones = result;
                vm.searchQuery = null;
            });
        }
    }
})();
