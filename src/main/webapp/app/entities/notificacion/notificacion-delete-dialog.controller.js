(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotificacionDeleteController',NotificacionDeleteController);

    NotificacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Notificacion'];

    function NotificacionDeleteController($uibModalInstance, entity, Notificacion) {
        var vm = this;

        vm.notificacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Notificacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
