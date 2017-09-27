(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        //En el stateProvider deberá llamarse el state de la siguiente forma: nombre+Custom. En esta caso utilizamos "ejemploActividadCustom"
        $stateProvider.state('verPublicacion', {
            parent: 'appCustom',
            url: '/verPublicacion/{id}/{tipo}',
            data: {
                css:'content/css/custom/visor.css',
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/visorPublicacion/visorPublicacion.html',
                    controller: 'VisorPublicacionCustomController',
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
