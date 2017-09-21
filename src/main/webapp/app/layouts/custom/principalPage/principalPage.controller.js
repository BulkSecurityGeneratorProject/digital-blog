(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PrincipalPageController',PrincipalPageController);
    PrincipalPageController.$inject=['$scope','$stateParams','PersonalizarCuenta','VerPerfil','Principal'];
    function PrincipalPageController($scope,$stateParams,PersonalizarCuenta,VerPerfil,Principal) {

        var vm = this;

    }
})();
