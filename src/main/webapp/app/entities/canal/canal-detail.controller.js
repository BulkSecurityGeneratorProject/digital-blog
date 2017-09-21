(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CanalDetailController', CanalDetailController);

    CanalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Canal'];

    function CanalDetailController($scope, $rootScope, $stateParams, previousState, entity, Canal) {
        var vm = this;

        vm.canal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:canalUpdate', function(event, result) {
            vm.canal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
