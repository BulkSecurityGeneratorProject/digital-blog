(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CanalController', CanalController);

    CanalController.$inject = ['Canal'];

    function CanalController(Canal) {

        var vm = this;

        vm.canals = [];

        loadAll();

        function loadAll() {
            Canal.query(function(result) {
                vm.canals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
