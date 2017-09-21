(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PaginaDialogController', PaginaDialogController);

    PaginaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pagina', 'Capitulo'];

    function PaginaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pagina, Capitulo) {
        var vm = this;

        vm.pagina = entity;
        vm.clear = clear;
        vm.save = save;
        vm.capitulos = Capitulo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pagina.id !== null) {
                Pagina.update(vm.pagina, onSaveSuccess, onSaveError);
            } else {
                Pagina.save(vm.pagina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:paginaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
