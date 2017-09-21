(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CoolaboradorDetailController', CoolaboradorDetailController);

    CoolaboradorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Coolaborador', 'Capitulo', 'Publicacion', 'Usuario'];

    function CoolaboradorDetailController($scope, $rootScope, $stateParams, previousState, entity, Coolaborador, Capitulo, Publicacion, Usuario) {
        var vm = this;

        vm.coolaborador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:coolaboradorUpdate', function(event, result) {
            vm.coolaborador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
