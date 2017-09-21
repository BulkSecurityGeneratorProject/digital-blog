(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CoolaboradorDialogController', CoolaboradorDialogController);

    CoolaboradorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Coolaborador', 'Capitulo', 'Publicacion', 'Usuario'];

    function CoolaboradorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Coolaborador, Capitulo, Publicacion, Usuario) {
        var vm = this;

        vm.coolaborador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.capitulos = Capitulo.query();
        vm.publicacions = Publicacion.query();
        vm.usuarios = Usuario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.coolaborador.id !== null) {
                Coolaborador.update(vm.coolaborador, onSaveSuccess, onSaveError);
            } else {
                Coolaborador.save(vm.coolaborador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:coolaboradorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
