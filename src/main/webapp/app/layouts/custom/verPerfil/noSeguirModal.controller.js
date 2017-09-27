(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NoSeguidoresController', NoSeguidoresController);

    NoSeguidoresController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance','DejarSeguirServiceShare'];

    function NoSeguidoresController ($timeout, $scope, $stateParams, $uibModalInstance,DejarSeguirServiceShare) {
        var vm = this;

        vm.clear = clear;
        vm.dejarSeguir = false;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        //Cierra el modal
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        /**
         * @author Maureen Leon
         * Elimina la relacion entre usuario y seguidores
         * @version 1.0
         */
        vm.dejarSeguir = function  () {
            vm.dejarSeguir = true;
            DejarSeguirServiceShare.enviarDejarSeguir(vm.dejarSeguir);
            $uibModalInstance.close();

        }

    }
})();
