(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('BibliotecaDeleteController',BibliotecaDeleteController);

    BibliotecaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Biblioteca'];

    function BibliotecaDeleteController($uibModalInstance, entity, Biblioteca) {
        var vm = this;

        vm.biblioteca = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Biblioteca.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
