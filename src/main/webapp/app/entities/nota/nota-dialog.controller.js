(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NotaDialogController', NotaDialogController);

    NotaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Nota', 'Pagina'];

    function NotaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Nota, Pagina) {
        var vm = this;

        vm.nota = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paginas = Pagina.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.nota.id !== null) {
                Nota.update(vm.nota, onSaveSuccess, onSaveError);
            } else {
                Nota.save(vm.nota, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:notaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
