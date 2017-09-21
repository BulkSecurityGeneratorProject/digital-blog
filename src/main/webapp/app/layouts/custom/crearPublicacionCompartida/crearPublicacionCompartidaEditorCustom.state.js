(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('publicacionCompartidaEditor', {
            parent: 'app',
            url: '/publicacionCompartidaEditor',
            data: {

            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/crearPublicacionCompartida/' +
                    'crearPublicacionCompartidaEditor.html',
                    controller: 'CrearPublicacionCompartidaEditorCustomController',
                    controllerAs: 'vm'
                }
            }
        })
    }

})();
