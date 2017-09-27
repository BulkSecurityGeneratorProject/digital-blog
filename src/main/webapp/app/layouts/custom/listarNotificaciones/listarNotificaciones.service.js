(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ListarNotificaciones', ListarNotificaciones);

    ListarNotificaciones.$inject = ['$resource','$http'];

    function ListarNotificaciones ($resource,$http) {
        return {
            obtenerNotificaciones: function (pathParams) {
                return $http.get('/apiCustom/obtenerNotificaciones/' + pathParams);
            },
            obtenerUsuariosNotificacion: function (pathParams) {
                return $http.get('/apiCustom/usuariosNotificacion/' + pathParams.ids);
            },
            obtenerNotificacionPublicacion: function (pathParams) {
                return $http.get('/apiCustom/notificacionPublicaciones/' + pathParams);
            },
            obtenerPublicaciones: function (pathParams) {
                return $http.get('/apiCustom/obtenerPublicacionesNotificacion/' + pathParams.ids);
            },
            getColaborativas: function (pathParams) {
                return $http.get('/apiCustom/notificacion/'+pathParams.id +'&'+ 3);
            },
            obtenerCapitulo: function(pathParams){
                return $http.get('/api/capitulos/'+pathParams.id);
            },
            getSolicitudes: function (pathParams) {
                return $http.get('/apiCustom/notificacion/' + pathParams.id +'&'+ pathParams.opc);

            },
            obtenerPublicacionesPorIdCapitulo: function (pathParams) {
                return $http.get('/apiCustom/obtenerPublicacionesIdCapitulo/' + pathParams.capitulos);

            },
            cambiarEstadoNotificacion: function (pathParams) {
                return $http.get('/apiCustom/cambiarEstadoNoticacion/' + pathParams.idNotificacion);
            },

            cambiarNotiLikeEstado:function(pathParams){
                 return $http.put('/apiCustom/cambiarNotiLikeEstado/' + pathParams.id);
            }
        }
    }
})();
