/**
 * Created by jose_ on 10/4/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .factory('CompartidaServiceShare', CompartidaServiceShare);

    CompartidaServiceShare.$inject = ['$rootScope'];

    function CompartidaServiceShare($rootScope) {
        var serviceShare = {};
        serviceShare.result = {};

        serviceShare.enviarSiONo = function (result) {
            this.result = result;
            this.obtenerResult();
        };
        serviceShare.obtenerResult = function () {
            $rootScope.$broadcast('compartida');
        };
        return serviceShare;
    }
})();


