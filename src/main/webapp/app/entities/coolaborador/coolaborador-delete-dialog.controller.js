(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CoolaboradorDeleteController',CoolaboradorDeleteController);

    CoolaboradorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Coolaborador'];

    function CoolaboradorDeleteController($uibModalInstance, entity, Coolaborador) {
        var vm = this;

        vm.coolaborador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Coolaborador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
