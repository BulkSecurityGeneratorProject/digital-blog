(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionDialogController', PublicacionDialogController);

    PublicacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Publicacion', 'Usuario', 'Categoria', 'Tema'];

    function PublicacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Publicacion, Usuario, Categoria, Tema) {
        var vm = this;

        vm.publicacion = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.usuarios = Usuario.query();
        vm.categorias = Categoria.query();
        vm.temas = Tema.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.publicacion.id !== null) {
                Publicacion.update(vm.publicacion, onSaveSuccess, onSaveError);
            } else {
                Publicacion.save(vm.publicacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('digitalBlogApp:publicacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFotopublicacion = function ($file, publicacion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        publicacion.fotopublicacion = base64Data;
                        publicacion.fotopublicacionContentType = $file.type;
                    });
                });
            }
        };

    }
})();
