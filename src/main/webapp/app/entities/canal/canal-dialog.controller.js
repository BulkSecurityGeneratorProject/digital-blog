(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CanalDialogController', CanalDialogController);

    CanalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Canal'];

    function CanalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Canal) {
        var vm = this;

        vm.canal = entity;
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
            if (vm.canal.id !== null) {
                Canal.update(vm.canal, onSaveSuccess, onSaveError);
            } else {
                Canal.save(vm.canal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:canalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
