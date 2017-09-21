(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RolePermisoDetailController', RolePermisoDetailController);

    RolePermisoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RolePermiso', 'Permiso', 'Rol'];

    function RolePermisoDetailController($scope, $rootScope, $stateParams, previousState, entity, RolePermiso, Permiso, Rol) {
        var vm = this;

        vm.rolePermiso = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:rolePermisoUpdate', function(event, result) {
            vm.rolePermiso = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
