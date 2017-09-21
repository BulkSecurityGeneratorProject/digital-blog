(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RespuestaDialogController', RespuestaDialogController);

    RespuestaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Respuesta', 'Comentario'];

    function RespuestaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Respuesta, Comentario) {
        var vm = this;

        vm.respuesta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comentarios = Comentario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.respuesta.id !== null) {
                Respuesta.update(vm.respuesta, onSaveSuccess, onSaveError);
            } else {
                Respuesta.save(vm.respuesta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:respuestaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
