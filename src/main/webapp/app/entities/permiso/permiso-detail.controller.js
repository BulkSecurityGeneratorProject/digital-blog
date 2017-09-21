(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PermisoDetailController', PermisoDetailController);

    PermisoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Permiso'];

    function PermisoDetailController($scope, $rootScope, $stateParams, previousState, entity, Permiso) {
        var vm = this;

        vm.permiso = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:permisoUpdate', function(event, result) {
            vm.permiso = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
