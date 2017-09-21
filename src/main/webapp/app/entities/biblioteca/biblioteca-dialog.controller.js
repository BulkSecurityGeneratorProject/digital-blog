(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('BibliotecaDialogController', BibliotecaDialogController);

    BibliotecaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Biblioteca', 'Usuario', 'Seccion'];

    function BibliotecaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Biblioteca, Usuario, Seccion) {
        var vm = this;

        vm.biblioteca = entity;
        vm.clear = clear;
        vm.save = save;
        vm.idusuariobs = Usuario.query({filter: 'biblioteca-is-null'});
        $q.all([vm.biblioteca.$promise, vm.idusuariobs.$promise]).then(function() {
            if (!vm.biblioteca.idUsuarioBId) {
                return $q.reject();
            }
            return Usuario.get({id : vm.biblioteca.idUsuarioBId}).$promise;
        }).then(function(idUsuarioB) {
            vm.idusuariobs.push(idUsuarioB);
        });
        vm.seccions = Seccion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.biblioteca.id !== null) {
                Biblioteca.update(vm.biblioteca, onSaveSuccess, onSaveError);
            } else {
                Biblioteca.save(vm.biblioteca, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:bibliotecaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
