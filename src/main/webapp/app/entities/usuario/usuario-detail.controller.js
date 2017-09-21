(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('UsuarioDetailController', UsuarioDetailController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Usuario', 'Publicacion', 'Rol'];

    function UsuarioDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Usuario, Publicacion, Rol) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('digitalBlogApp:usuarioUpdate', function(event, result) {
            vm.usuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
