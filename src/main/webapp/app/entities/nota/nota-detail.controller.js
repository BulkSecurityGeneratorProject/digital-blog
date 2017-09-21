(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotaDetailController', NotaDetailController);

    NotaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Nota', 'Pagina'];

    function NotaDetailController($scope, $rootScope, $stateParams, previousState, entity, Nota, Pagina) {
        var vm = this;

        vm.nota = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:notaUpdate', function(event, result) {
            vm.nota = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
