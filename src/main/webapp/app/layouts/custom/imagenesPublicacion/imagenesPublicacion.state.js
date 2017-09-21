(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('imagenPublicacion', {
            parent: 'app',
            url: '/imagenPublicacion',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/imagenesPublicacion/imagenesPublicacion.html',
                    controller: 'ImagenPublicacionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();

