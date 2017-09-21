(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('LikeTDialogController', LikeTDialogController);

    LikeTDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LikeT', 'Usuario', 'Publicacion'];

    function LikeTDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LikeT, Usuario, Publicacion) {
        var vm = this;

        vm.likeT = entity;
        vm.clear = clear;
        vm.save = save;
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
            if (vm.likeT.id !== null) {
                LikeT.update(vm.likeT, onSaveSuccess, onSaveError);
            } else {
                LikeT.save(vm.likeT, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:likeTUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
