/**
 * Created by Maureen on 4/4/2017.
 */
(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('configPublicacionCompartida', {
            parent: 'app',
            url: '/configPublicacionCompartida',
            data: {},
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/configurarPublicacionCompartida/configPublicacionCompartida.html',
                    controller: 'configPublicacionCompartidaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                publicacion: function (){
                    return {
                        titulo: "",
                        descripcion: "",
                        imagen: "",
                        tema: "",
                        categoria: ""
                    }
                }
            }
        })

        .state('asignarColaboradores', {
            parent: 'configPublicacionCompartida',
            url: '/asignarColaboradores',
            data: {},
            views: {
                'content@': {
                    templateUrl:  'app/layouts/custom/configurarPublicacionCompartida/asignarColaboradores/' +
                    'asignarColaboradores.html',
                    controller: 'asignarColaboradoresController',
                    controllerAs: 'vm'
                }
            }
        });
    }

})();
