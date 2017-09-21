(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('personalizarPerfilCustom', {
            parent: 'app',
            url: '/personalizarPerfilCustom',
            data: {
                css:'content/css/custom/personalizarPerfil.css',
                authorities: ['ROLE_USER']

        },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/personalizarPerfil/personalizarPerfilCustom.html',
                    controller: 'PersonalizarPerfilCustomController',
                    controllerAs: 'vm'
                }
            }, resolve: {
                entity: function () {
                    return {
                        nombre: null,
                        primerApelldio: null,
                        segundoApellido: null,
                        edad: null,
                        correo: null,
                        descripcion: null,
                        fotoperfil: null,
                        fotoperfilContentType: null,
                        estado: null,
                        idJHIUser: null,
                        fechaNacimiento: null,
                        id: null
                    };
                }
            }
        })
    }

})();


