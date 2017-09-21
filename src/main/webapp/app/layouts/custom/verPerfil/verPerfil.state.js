(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('verPerfil', {
            parent: 'app',
            url: '/verPerfil/:id',
            data: {
                css:'content/css/custom/verPerfil.css',
                authorities: ['ROLE_USER']

            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/verPerfil/verPerfil.html',
                    controller: 'VerPerfilController',
                    controllerAs: 'vm'
                }
            }

        })
    }

})();
