(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listarNotificaciones', {
            parent: 'app',
            url: '/listarNotificaciones',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/listarNotificaciones/listarNotificaciones.html',
                    controller: 'ListarNotificacionesController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
