(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaDeleteController',PublicacionReportadaDeleteController);

    PublicacionReportadaDeleteController.$inject = ['$uibModalInstance', 'entity', 'PublicacionReportada'];

    function PublicacionReportadaDeleteController($uibModalInstance, entity, PublicacionReportada) {
        var vm = this;

        vm.publicacionReportada = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PublicacionReportada.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
