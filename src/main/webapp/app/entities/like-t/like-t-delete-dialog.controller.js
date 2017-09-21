(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('LikeTDeleteController',LikeTDeleteController);

    LikeTDeleteController.$inject = ['$uibModalInstance', 'entity', 'LikeT'];

    function LikeTDeleteController($uibModalInstance, entity, LikeT) {
        var vm = this;

        vm.likeT = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LikeT.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
