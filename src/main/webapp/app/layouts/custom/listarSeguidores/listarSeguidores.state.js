(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listarSeguidores', {
            parent: 'app',
            url: '/listarSeguidores/:id',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/listarSeguidores/listarSeguidores.html',
                    controller: 'ListarSeguidoresController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
