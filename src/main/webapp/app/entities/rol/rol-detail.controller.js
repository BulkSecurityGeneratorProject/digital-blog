(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolDetailController', RolDetailController);

    RolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rol', 'Usuario'];

    function RolDetailController($scope, $rootScope, $stateParams, previousState, entity, Rol, Usuario) {
        var vm = this;

        vm.rol = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:rolUpdate', function(event, result) {
            vm.rol = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
