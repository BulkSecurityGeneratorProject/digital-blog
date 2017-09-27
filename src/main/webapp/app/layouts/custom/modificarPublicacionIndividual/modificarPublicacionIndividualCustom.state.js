(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('modificarPublicacionIndividual', {
            parent: 'app',
            url: '/modificarPublicacionIndividual',
            data: {
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/modificarPublicacionIndividual/modificarPublicacionIndividual.html',
                    controller: 'ModificarPublicacionCustomController',
                    controllerAs: 'vm'
                }
            },
            resolve: {

            }
        })
    }

})();
