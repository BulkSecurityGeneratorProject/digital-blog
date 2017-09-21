(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('LikeTController', LikeTController);

    LikeTController.$inject = ['LikeT'];

    function LikeTController(LikeT) {

        var vm = this;

        vm.likeTS = [];

        loadAll();

        function loadAll() {
            LikeT.query(function(result) {
                vm.likeTS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
