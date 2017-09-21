(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PaginaDeleteController',PaginaDeleteController);

    PaginaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pagina'];

    function PaginaDeleteController($uibModalInstance, entity, Pagina) {
        var vm = this;

        vm.pagina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pagina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
