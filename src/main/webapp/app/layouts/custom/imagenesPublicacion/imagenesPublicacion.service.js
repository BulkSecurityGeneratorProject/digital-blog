(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ImagenPublicacion', ImagenPublicacion);

    ImagenPublicacion.$inject = ['$resource','$http'];

    function ImagenPublicacion ($resource,$http) {
        return {
            guardarImagenes: function (pathParams) {
                console.log(pathParams);
            return $http.post('/apiCustom/savePictures/',pathParams);
             }
        }
    }
})();
