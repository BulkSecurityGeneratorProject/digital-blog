(function() {
    // 'use strict';
    angular
        .module('digitalBlogApp')
        .factory('DejarSeguirServiceShare', DejarSeguirServiceShare);
    DejarSeguirServiceShare.$inject = ['$rootScope'];
    function DejarSeguirServiceShare ($rootScope) {


        var serviceShare = {};

        serviceShare.dejarSeguir;

        serviceShare.enviarDejarSeguir = function(ds){
            this.dejarSeguir = ds;
            this.recibirDejarSeguir();
        };

        serviceShare.recibirDejarSeguir = function(){
            $rootScope.$broadcast('DejarSeguirBroadcast');
        };

        return serviceShare;



    }

})();
