(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('BibliotecaDetailController', BibliotecaDetailController);

    BibliotecaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Biblioteca', 'Usuario', 'Seccion'];

    function BibliotecaDetailController($scope, $rootScope, $stateParams, previousState, entity, Biblioteca, Usuario, Seccion) {
        var vm = this;

        vm.biblioteca = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:bibliotecaUpdate', function(event, result) {
            vm.biblioteca = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
