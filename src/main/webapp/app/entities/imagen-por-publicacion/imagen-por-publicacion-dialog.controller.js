(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ImagenPorPublicacionDialogController', ImagenPorPublicacionDialogController);

    ImagenPorPublicacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ImagenPorPublicacion'];

    function ImagenPorPublicacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ImagenPorPublicacion) {
        var vm = this;

        vm.imagenPorPublicacion = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.imagenPorPublicacion.id !== null) {
                ImagenPorPublicacion.update(vm.imagenPorPublicacion, onSaveSuccess, onSaveError);
            } else {
                ImagenPorPublicacion.save(vm.imagenPorPublicacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:imagenPorPublicacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImagen = function ($file, imagenPorPublicacion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        imagenPorPublicacion.imagen = base64Data;
                        imagenPorPublicacion.imagenContentType = $file.type;
                    });
                });
            }
        };

    }
})();
