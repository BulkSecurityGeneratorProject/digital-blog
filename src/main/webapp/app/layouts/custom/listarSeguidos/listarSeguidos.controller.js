(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ListarSeguidosController', ListarSeguidosController);

    ListarSeguidosController.$inject = ['ListarSeguidos','$stateParams'];

    function ListarSeguidosController (ListarSeguidos,$stateParams) {
        var vm = this;
        vm.seguidos;
        vm.usuarioid =  $stateParams.id;
        vm.errorConection = false;

        /**
         * @author Maureen Leon
         * Obtiene los usuarios que un usuario especifico ha seguido
         * @version 1.0
         */
        ListarSeguidos.obtenerSeguidos(vm.usuarioid).success(function(response){
            vm.seguidos=response;
        }).error(function (error){
            if(error.description == "Internal server error"){
                vm.errorConection ==  true;
            }
        });


    }
})();
