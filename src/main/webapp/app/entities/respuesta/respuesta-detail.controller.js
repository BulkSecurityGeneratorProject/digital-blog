(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RespuestaDetailController', RespuestaDetailController);

    RespuestaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Respuesta', 'Comentario'];

    function RespuestaDetailController($scope, $rootScope, $stateParams, previousState, entity, Respuesta, Comentario) {
        var vm = this;

        vm.respuesta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:respuestaUpdate', function(event, result) {
            vm.respuesta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
