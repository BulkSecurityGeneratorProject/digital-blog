(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('TemaDialogController', TemaDialogController);

    TemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tema'];

    function TemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tema) {
        var vm = this;

        vm.tema = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tema.id !== null) {
                Tema.update(vm.tema, onSaveSuccess, onSaveError);
            } else {
                Tema.save(vm.tema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:temaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
