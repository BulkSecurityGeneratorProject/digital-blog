(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeguidorController', SeguidorController);

    SeguidorController.$inject = ['Seguidor'];

    function SeguidorController(Seguidor) {

        var vm = this;

        vm.seguidors = [];

        loadAll();

        function loadAll() {
            Seguidor.query(function(result) {
                vm.seguidors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
