(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listarSeguidos', {
            parent: 'app',
            url: '/listarSeguidos/:id',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/listarSeguidos/listarSeguidos.html',
                    controller: 'ListarSeguidosController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();

