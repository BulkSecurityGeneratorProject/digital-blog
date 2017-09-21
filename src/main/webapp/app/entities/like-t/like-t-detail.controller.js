(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('LikeTDetailController', LikeTDetailController);

    LikeTDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LikeT', 'Usuario', 'Publicacion'];

    function LikeTDetailController($scope, $rootScope, $stateParams, previousState, entity, LikeT, Usuario, Publicacion) {
        var vm = this;

        vm.likeT = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:likeTUpdate', function(event, result) {
            vm.likeT = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
