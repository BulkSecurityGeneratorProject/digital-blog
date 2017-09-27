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
            console.log(vm.listaUsuarios);
            console.log("EN LA VISTA LISTAR USUARIOS");

        }).error(function (error){
            console.log("Problema inesperado al traer todos los usuarios");
        });



    }
})();
