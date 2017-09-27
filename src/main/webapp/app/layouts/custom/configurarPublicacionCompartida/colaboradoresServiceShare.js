
(function() {
    // 'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ColaboradorServiceShare', ColaboradorServiceShare);
    ColaboradorServiceShare.$inject = ['$rootScope'];
    function ColaboradorServiceShare ($rootScope) {

        var serviceShare = {};

        serviceShare.colaboradores = [];
        serviceShare.seguidores = [];

        serviceShare.enviarColaboradores = function(col){
            this.colaboradores = col;
            this.recibirColaborador();
        };

        serviceShare.recibirColaborador= function(){
            $rootScope.$broadcast('colaboradorBroadcast');
        };

        serviceShare.enviarSeguidos = function(seg){
            this.seguidores = seg;
            this.recibirSeguidos();
        };

        serviceShare.recibirSeguidos= function(){
            $rootScope.$broadcast('seguidoresBroadcast');
        };

        return serviceShare;

    }

})();
