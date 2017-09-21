(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SuscripcionesDetailController', SuscripcionesDetailController);

    SuscripcionesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Suscripciones'];

    function SuscripcionesDetailController($scope, $rootScope, $stateParams, previousState, entity, Suscripciones) {
        var vm = this;

        vm.suscripciones = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:suscripcionesUpdate', function(event, result) {
            vm.suscripciones = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
