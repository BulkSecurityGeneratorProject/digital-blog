
(function() {
   // 'use strict';
    angular
        .module('digitalBlogApp')
        .factory('PublicacionServiceShare', PublicacionServiceShare);
    PublicacionServiceShare.$inject = ['$rootScope'];
    function PublicacionServiceShare ($rootScope) {

        var serviceShare = {};

        serviceShare.publicacion = {};
        serviceShare.tipoPubli = {};

        /**
         *  @Param: tipo es para ver si es una publicación entera o si es solo un capitulo (anterior)
         *  @author José Nuñez
         *  @version 1.0
         */


        serviceShare.enviarPublicacion = function(pb, tipo){
           this.publicacion = pb;
           this.tipoPubli = tipo;
           this.recibirPublicacion();
        };

        serviceShare.recibirPublicacion = function(){
           $rootScope.$broadcast('publicacionBroadcast');
        };

        return serviceShare;

    }

})();

