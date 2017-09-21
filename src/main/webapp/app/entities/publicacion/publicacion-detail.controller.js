(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionDetailController', PublicacionDetailController);

    PublicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Publicacion', 'Usuario', 'Categoria', 'Tema'];

    function PublicacionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Publicacion, Usuario, Categoria, Tema) {
        var vm = this;

        vm.publicacion = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('digitalBlogApp:publicacionUpdate', function(event, result) {
            vm.publicacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
