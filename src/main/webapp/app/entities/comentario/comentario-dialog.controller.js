(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ComentarioDialogController', ComentarioDialogController);

    ComentarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Comentario', 'Respuesta', 'Usuario', 'Publicacion'];

    function ComentarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Comentario, Respuesta, Usuario, Publicacion) {
        var vm = this;

        vm.comentario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idcomentariorespuestas = Respuesta.query({filter: 'comentario-is-null'});
        $q.all([vm.comentario.$promise, vm.idcomentariorespuestas.$promise]).then(function() {
            if (!vm.comentario.idComentarioRespuestaId) {
                return $q.reject();
            }
            return Respuesta.get({id : vm.comentario.idComentarioRespuestaId}).$promise;
        }).then(function(idComentarioRespuesta) {
            vm.idcomentariorespuestas.push(idComentarioRespuesta);
        });
        vm.usuarios = Usuario.query();
        vm.publicacions = Publicacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comentario.id !== null) {
                Comentario.update(vm.comentario, onSaveSuccess, onSaveError);
            } else {
                Comentario.save(vm.comentario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:comentarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
