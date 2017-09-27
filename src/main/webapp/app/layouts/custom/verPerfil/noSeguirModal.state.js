(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('noSeguir', {
            parent: 'VerPerfil',
            url: '/noSeguir',
            data: {
                css:'content/css/custom/verPerfil.css',
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/verPerfil/noSeguirModal.html',
                    controller: 'NoSeguidoresController',
                    controllerAs: 'vm'
                }
            }
        })
    }

})();
