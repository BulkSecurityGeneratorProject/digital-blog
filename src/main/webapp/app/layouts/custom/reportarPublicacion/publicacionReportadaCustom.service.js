(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('PublicacionReportadaCustom', PublicacionReportadaCustom);

    PublicacionReportadaCustom.$inject = ['$http'];

    function PublicacionReportadaCustom ($http) {
        var resourceUrl =  'api/publicacion-reportadas/:id';

        return {
            cargarPublicacionReportadaPorIdPublicacion:function (id) {
                return $http.get('apiCustom/publicacionesReportadasCustom/'+ id);
            },
            conseguirJhiUserPorIdUsuario: function (pathParams) {
                return $http.get('apiCustom/obtenerJhiUser/'+ pathParams.id);
            }

        }

    }
})();
