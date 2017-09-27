(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        //En el stateProvider deberá llamarse el state de la siguiente forma: nombre+Custom. En esta caso utilizamos "ejemploActividadCustom"
        $stateProvider.state('crearPublicacionIndividual', {
            parent: 'app',
            url: '/crearPublicacionIndividual',
            data: {
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/crearPublicacionIndividual/crearPublicacionIndividual.html',
                    controller: 'CrearPublicacionIndividualCustomController',
                    controllerAs: 'vm'
                }
            },
            resolve: {

            }
        })
    }

})();
