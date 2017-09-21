(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaController', PublicacionReportadaController);

    PublicacionReportadaController.$inject = ['PublicacionReportada'];

    function PublicacionReportadaController(PublicacionReportada) {

        var vm = this;

        vm.publicacionReportadas = [];

        loadAll();

        function loadAll() {
            PublicacionReportada.query(function(result) {
                vm.publicacionReportadas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
