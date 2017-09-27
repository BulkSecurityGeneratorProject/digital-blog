(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ListarUsuarios', ListarUsuarios);

    ListarUsuarios.$inject = ['$http'];
    function ListarUsuarios ($http) {
        return {
            getAllUsuarios: function () {
                return $http.get('/apiCustom/getAllUsers');
            }
        }
    }
})();

