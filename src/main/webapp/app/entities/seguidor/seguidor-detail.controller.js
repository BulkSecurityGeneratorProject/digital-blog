(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeguidorDetailController', SeguidorDetailController);

    SeguidorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seguidor', 'Usuario'];

    function SeguidorDetailController($scope, $rootScope, $stateParams, previousState, entity, Seguidor, Usuario) {
        var vm = this;

        vm.seguidor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:seguidorUpdate', function(event, result) {
            vm.seguidor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
