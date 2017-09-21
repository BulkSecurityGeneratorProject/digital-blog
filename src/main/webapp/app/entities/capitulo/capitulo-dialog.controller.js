(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CapituloDialogController', CapituloDialogController);

    CapituloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Capitulo', 'Publicacion'];

    function CapituloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Capitulo, Publicacion) {
        var vm = this;

        vm.capitulo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.publicacions = Publicacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.capitulo.id !== null) {
                Capitulo.update(vm.capitulo, onSaveSuccess, onSaveError);
            } else {
                Capitulo.save(vm.capitulo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:capituloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
