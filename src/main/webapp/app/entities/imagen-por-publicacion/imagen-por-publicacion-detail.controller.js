(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ImagenPorPublicacionDetailController', ImagenPorPublicacionDetailController);

    ImagenPorPublicacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ImagenPorPublicacion'];

    function ImagenPorPublicacionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ImagenPorPublicacion) {
        var vm = this;

        vm.imagenPorPublicacion = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('digitalBlogApp:imagenPorPublicacionUpdate', function(event, result) {
            vm.imagenPorPublicacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
