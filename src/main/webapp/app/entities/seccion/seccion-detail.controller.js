(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionDetailController', SeccionDetailController);

    SeccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seccion', 'Biblioteca'];

    function SeccionDetailController($scope, $rootScope, $stateParams, previousState, entity, Seccion, Biblioteca) {
        var vm = this;

        vm.seccion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:seccionUpdate', function(event, result) {
            vm.seccion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
