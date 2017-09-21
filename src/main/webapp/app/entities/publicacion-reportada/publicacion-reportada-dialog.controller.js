(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaDialogController', PublicacionReportadaDialogController);

    PublicacionReportadaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PublicacionReportada'];

    function PublicacionReportadaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PublicacionReportada) {
        var vm = this;

        vm.publicacionReportada = entity;
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
            if (vm.publicacionReportada.id !== null) {
                PublicacionReportada.update(vm.publicacionReportada, onSaveSuccess, onSaveError);
            } else {
                PublicacionReportada.save(vm.publicacionReportada, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:publicacionReportadaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
