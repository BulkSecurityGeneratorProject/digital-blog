(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .factory('SeccionServiceShare', SeccionServiceShare);

    SeccionServiceShare.$inject = ['$rootScope'];

    /**
     * @author Eduardo Guerrero
     * Envia el id de la biblioteca del usuario logueado
     * @version 1.0
     */
    function SeccionServiceShare($rootScope) {
            var serviceShare = {};
            serviceShare.seccion = {};

                serviceShare.enviarIdBiblioteca = function (id) {
                    this.seccion = id;
                    this.obtenerSeccion();
                };
                serviceShare.obtenerSeccion = function () {
                    $rootScope.$broadcast('seccionBroadcast');
                };
              return serviceShare;
    }
})();

