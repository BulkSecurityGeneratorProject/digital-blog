(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('TemaDetailController', TemaDetailController);

    TemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tema'];

    function TemaDetailController($scope, $rootScope, $stateParams, previousState, entity, Tema) {
        var vm = this;

        vm.tema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:temaUpdate', function(event, result) {
            vm.tema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
