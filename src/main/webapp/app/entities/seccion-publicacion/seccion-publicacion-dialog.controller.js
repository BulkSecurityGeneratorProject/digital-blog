(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionPublicacionDialogController', SeccionPublicacionDialogController);

    SeccionPublicacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SeccionPublicacion', 'Seccion', 'Publicacion'];

    function SeccionPublicacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SeccionPublicacion, Seccion, Publicacion) {
        var vm = this;

        vm.seccionPublicacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seccions = Seccion.query();
        vm.publicacions = Publicacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seccionPublicacion.id !== null) {
                SeccionPublicacion.update(vm.seccionPublicacion, onSaveSuccess, onSaveError);
            } else {
                SeccionPublicacion.save(vm.seccionPublicacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:seccionPublicacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
