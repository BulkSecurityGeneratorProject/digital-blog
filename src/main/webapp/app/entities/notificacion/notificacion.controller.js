(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotificacionController', NotificacionController);

    NotificacionController.$inject = ['Notificacion'];

    function NotificacionController(Notificacion) {

        var vm = this;

        vm.notificacions = [];

        loadAll();

        function loadAll() {
            Notificacion.query(function(result) {
                vm.notificacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
