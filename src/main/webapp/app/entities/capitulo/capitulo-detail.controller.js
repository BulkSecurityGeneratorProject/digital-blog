(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CapituloDetailController', CapituloDetailController);

    CapituloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Capitulo', 'Publicacion'];

    function CapituloDetailController($scope, $rootScope, $stateParams, previousState, entity, Capitulo, Publicacion) {
        var vm = this;

        vm.capitulo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:capituloUpdate', function(event, result) {
            vm.capitulo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
