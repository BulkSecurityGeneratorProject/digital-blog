(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ImagenPublicacionController', ImagenPublicacionController);

    ImagenPublicacionController.$inject = ['$scope','$state','DataUtils','$stateParams','ImagenPublicacion'];

    function ImagenPublicacionController ($scope,$state,DataUtils,$stateParams,ImagenPublicacion) {
        var vm = this;
        vm.fotos=[];
        vm.publicacion={};

        function save (publi){
            console.log(publi);
            vm.fotos.push(publi);
        }

        /**
         * @author Maureen Leon
         * @param $index
         */
        vm.getImage=function($index){
        }
        /**
         * @author Maureen Leon
         * @param $index
         */
        vm.delete=function($index){
            vm.fotos.pop($index);
        }
        /**
         * @author Maureen Leon
         */
        vm.guardar= function(){
            ImagenPublicacion.guardarImagenes(vm.fotos).success(function(response){
                vm.fotos=response;
                console.log(response);
            }).error(function (error){
            });
        }
        /**
         * @author Maureen Leon
         * @param $file
         * @param publicacion
         */
        vm.setFotopublicacion = function ($file, publicacion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                var publi={};
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        publi={
                            fotopublicacion: publicacion.fotopublicacion = base64Data,
                            fotopublicacionContentType: publicacion.fotopublicacionContentType = $file.type
                        };
                      save(publi);
                    });
                });
            }
        };

    }
})();
