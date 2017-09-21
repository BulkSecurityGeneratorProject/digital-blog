(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('comentarioSobrePublicacionService', comentarioSobrePublicacionService);

    comentarioSobrePublicacionService.$inject = ['$resource','$http'];

    function comentarioSobrePublicacionService ($resource,$http) {
        return {
            getComentarioSobrePublicacion: function(pathParams){
                return $http.get('apiCustom/comentarioSobrePublicacion/' + pathParams.idPublicacion)
            },
            salvarComentario:function (pathParams) {
                return  $http.post('api/comentarios', pathParams.comentario)
            }
        }
    }
})();
