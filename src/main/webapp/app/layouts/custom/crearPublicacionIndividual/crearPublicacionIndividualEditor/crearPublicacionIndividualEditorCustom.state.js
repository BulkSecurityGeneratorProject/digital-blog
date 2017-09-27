(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        //En el stateProvider deberá llamarse el state de la siguiente forma: nombre+Custom. En esta caso utilizamos "ejemploActividadCustom"
        $stateProvider.state('crearPublicacionIndividualEditor', {
            parent: 'app',
            url: '/crearPublicacionIndividualEditor',
            data: {

            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/crearPublicacionIndividual/crearPublicacionIndividualEditor/' +
                    'crearPublicacionIndividualEditor.html',
                    controller: 'CrearPublicacionIndividualEditorCustomController',
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
    }

})();
