(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listarUsuarios', {
            parent: 'app',
            url: '/listarUsuarios',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/listarUsuarios/listarUsuarios.html',
                    controller: 'ListarUsuariosController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
