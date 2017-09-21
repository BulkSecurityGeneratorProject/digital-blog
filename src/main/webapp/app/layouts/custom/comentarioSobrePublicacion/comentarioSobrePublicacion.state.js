
(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('comentarioSobrePublicacion', {
            parent: 'app',
            url: '/comentarioSobrePublicacion/:idPublicacion',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/comentarioSobrePublicacion/comentarioSobrePublicacion.html',
                    controller: 'comentarioSobrePublicacionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})()
