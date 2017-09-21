(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('TemaDeleteController',TemaDeleteController);

    TemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tema'];

    function TemaDeleteController($uibModalInstance, entity, Tema) {
        var vm = this;

        vm.tema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
