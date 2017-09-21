(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ListarSeguidoresController', ListarSeguidoresController);

    ListarSeguidoresController.$inject = ['$scope','ListarSeguidores','$stateParams'];

    function ListarSeguidoresController ($scope,ListarSeguidores,$stateParams) {
        var vm = this;
        vm.seguidores;
        vm.errorConection = false;

        vm.usuarioid =  $stateParams.id;
        /**
         * @author Maureen Leon
         * Obtiene los usuarios que han seguido a un usuario especifico
         * @version 1.0
         */
        ListarSeguidores.obtenerSeguidores(vm.usuarioid).success(function(response){
            vm.seguidores=response;

        }).error(function (error){
            if(error.description == "Internal server error"){
                vm.errorConection = true;
            }
            if(error.description == "Not Found"){
                vm.errorConection = true;
                $state.go('notFound');
            }

        });


    }
})();

