(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('PersonalizarCuenta', PersonalizarCuenta);

    PersonalizarCuenta.$inject = ['$resource','$http'];

    function PersonalizarCuenta ($resource,$http) {
        return {
            obtenerUsuarioNormal: function (pathParams) {
                return $http.get('/apiCustom/usuarios/'+pathParams);
            },
            update: function (pathParams) {
                return $http.put('/apiCustom/usuariosUpdate', pathParams);
            },
            obtenerNotificcionesNoLeidas: function (pathParams) {
                return $http.get('/apiCustom/notificcionesNoLeidas/'+pathParams.id);

            }
        }
    }
})();
