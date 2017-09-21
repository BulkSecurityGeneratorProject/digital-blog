(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeguidorDeleteController',SeguidorDeleteController);

    SeguidorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seguidor'];

    function SeguidorDeleteController($uibModalInstance, entity, Seguidor) {
        var vm = this;

        vm.seguidor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seguidor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
