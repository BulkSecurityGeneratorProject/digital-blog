(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .factory('BibliotecaGeneralServiceShare', BibliotecaGeneralServiceShare);

    BibliotecaGeneralServiceShare.$inject = ['$rootScope'];

    function BibliotecaGeneralServiceShare($rootScope) {
            var serviceShare = {};
            serviceShare.publicacion = {};

        /**
         * @author Eduardo Guerrero
         * Se utiliza para enviar una publicacion a cualquier parte de la aplicacion
         * siempre y cuando este escuchando.
         * @param publicacion
         * @version 1.0
         */
        serviceShare.enviarPublicacion = function (publicacion) {
                    this.publicacion = publicacion;
                    this.obtenerPublicacion();
                };
                serviceShare.obtenerPublicacion = function () {
                    $rootScope.$broadcast('bibliotecaGeneralBroadcast');
                };
              return serviceShare;
    }
})();

