(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listarPublicacionesPorSeccion', {
            parent: 'app',
            url: '/listarPublicacionesPorSeccion',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/listarPublicacionesSeccion/listarPublicacionesSeccion.html',
                    controller: 'listarPublicacionesPorSeccionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})()
