(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ComentarioDetailController', ComentarioDetailController);

    ComentarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comentario', 'Respuesta', 'Usuario', 'Publicacion'];

    function ComentarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Comentario, Respuesta, Usuario, Publicacion) {
        var vm = this;

        vm.comentario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:comentarioUpdate', function(event, result) {
            vm.comentario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
