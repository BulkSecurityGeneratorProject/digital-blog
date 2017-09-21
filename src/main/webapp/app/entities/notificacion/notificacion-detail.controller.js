(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotificacionDetailController', NotificacionDetailController);

    NotificacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Notificacion'];

    function NotificacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Notificacion) {
        var vm = this;

        vm.notificacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:notificacionUpdate', function(event, result) {
            vm.notificacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
