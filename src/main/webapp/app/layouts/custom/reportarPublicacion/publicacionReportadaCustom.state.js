(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publicacionReportada', {
            parent: 'app',
            url: '/publicacionReportada',
            data: {
                authorities: ['ROLE_MODERATOR'],
                pageTitle: 'PublicacionReportadas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/reportarPublicacion/publicacionReportadas.html',
                    controller: 'PublicacionReportadaCustomController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
