(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SuscripcionesDeleteController',SuscripcionesDeleteController);

    SuscripcionesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Suscripciones'];

    function SuscripcionesDeleteController($uibModalInstance, entity, Suscripciones) {
        var vm = this;

        vm.suscripciones = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Suscripciones.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
