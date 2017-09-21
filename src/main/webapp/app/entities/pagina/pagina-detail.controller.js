(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PaginaDetailController', PaginaDetailController);

    PaginaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pagina', 'Capitulo'];

    function PaginaDetailController($scope, $rootScope, $stateParams, previousState, entity, Pagina, Capitulo) {
        var vm = this;

        vm.pagina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:paginaUpdate', function(event, result) {
            vm.pagina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
