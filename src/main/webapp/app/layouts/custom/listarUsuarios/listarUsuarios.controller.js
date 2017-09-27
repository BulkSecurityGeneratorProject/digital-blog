(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ListarUsuariosController', ListarUsuariosController);

    ListarUsuariosController.$inject = ['$scope','ListarUsuarios'];

    function ListarUsuariosController ($scope,ListarUsuarios) {
        var vm = this;
        vm.listaUsuarios;

        /**
         * @author Chistopher Sullivan
         * Obtiene todos los usuarios con estado valido
         * @version 1.0
         */
        ListarUsuarios.getAllUsuarios().success(function(response){
            vm.listaUsuarios=response;

        }).error(function (error){
        });



    }
})();
