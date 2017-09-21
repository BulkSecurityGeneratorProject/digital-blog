(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('bibliotecaGeneral', {
            parent: 'app',
            url: '/bibliotecaGeneral',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Bibliotecas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/bibliotecaGeneral/bibliotecaGeneral.html',
                    controller: 'BibliotecaGeneralCustomController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })

    }

})();
