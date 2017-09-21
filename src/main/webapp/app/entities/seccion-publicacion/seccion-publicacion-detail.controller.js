(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionPublicacionDetailController', SeccionPublicacionDetailController);

    SeccionPublicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SeccionPublicacion', 'Seccion', 'Publicacion'];

    function SeccionPublicacionDetailController($scope, $rootScope, $stateParams, previousState, entity, SeccionPublicacion, Seccion, Publicacion) {
        var vm = this;

        vm.seccionPublicacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:seccionPublicacionUpdate', function(event, result) {
            vm.seccionPublicacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
