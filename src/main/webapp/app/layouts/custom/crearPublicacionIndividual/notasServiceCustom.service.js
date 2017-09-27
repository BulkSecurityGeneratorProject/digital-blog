(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('NotaService', NotaService);

    NotaService.$inject = ['$resource','$http'];

    function NotaService ($resource,$http) {
        return {
            getNotaPagina: function(pathParams){
                return $http.get('apiCustom/notaPagina/' + pathParams.idPagina);
            }
        }
    }
})();
