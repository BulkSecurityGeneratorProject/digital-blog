(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('comentarioSobrePublicacionController', comentarioSobrePublicacionController);

    comentarioSobrePublicacionController.$inject = ['$window','Respuesta', 'Comentario', 'Publicacion', '$stateParams', '$scope', 'Principal', 'LoginService', '$state', 'comentarioSobrePublicacionService', 'PersonalizarCuenta'];

    function comentarioSobrePublicacionController($window,Respuesta, Comentario, Publicacion, $stateParams, $scope, Principal, LoginService, $state, comentarioSobrePublicacionService, PersonalizarCuenta) {
        var vm = this;
        vm.idPublicacion = $stateParams.idPublicacion;
        vm.publicacion;
        vm.textoComentado;
        vm.usuarioComentando;
        vm.usuario;
        vm.comentarioMostrado;
        vm.comentarioMostradoCopia;
        vm.textoRespondido;
        vm.escribirResp = false;
        vm.comentarioCopia = null;

        vm.comentario = {
            id: null,
            contenido: null,
            idComentarioUId: null,
            idComentarioPId: null
        };

        vm.respuesta = {
            id: null,
            contenido: null,
            idComentarioRId: null
        }
        Principal.identity().then(function (account) {
            vm.usuario = account;
            obtenerUsuarioNormal()
        });
        function obtenerUsuarioNormal() {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.usuario.id).success(function (response) {
                vm.usuarioComentando = response;
            }).error(function (error) {
            });
        }

        getPublicacion();
        /**
         * @author Christopher Sullivan
         * obtine la pulicacion con el id de la publicacion pasado por pathparams
         * @version 1.0
         */
        function getPublicacion() {

            vm.publicacion = Publicacion.get({id: vm.idPublicacion});
            getComentario();
        }

        /**
         * @author Christopher Sullivan
         * obtiene todos los comentrios
         * @version 1.0
         */
        function getComentario() {
            comentarioSobrePublicacionService.getComentarioSobrePublicacion({idPublicacion: vm.idPublicacion}).success(function (responce) {
                vm.comentarioMostrado = responce;
                vm.errorConnection = false;
            })
        }
        vm.errorConnection = false;

        /**
         * @author Christopher Sullivan
         * salva el comentario hecho
         * @version 1.0
         */
        vm.salvarComentario = function () {
            if (vm.textoComentado != undefined) {
                vm.isSaving = true;
                vm.comentario.contenido = vm.textoComentado;
                vm.comentario.idComentarioPId = vm.publicacion.id;
                vm.comentario.idComentarioUId = vm.usuarioComentando.id;
                //Comentario.save(vm.comentario, onSaveSuccess(), onSaveError())
                comentarioSobrePublicacionService.salvarComentario({comentario:vm.comentario}).success(function () {
                    vm.textoComentado=null;
                    getComentario();

                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 500) {
                        vm.errorConnection = true;
                        vm.error = 'ERROR';
                    }else {
                        vm.errorConnection = true;
                        vm.error = 'ERROR';
                    }
                });
            }
        }

        /**
         * @author Christopher Sullivan
         * @param result
         * @version 1.0
         */
        function onSaveSuccess(result) {
            $scope.$emit('digitalBlogApp:comentarioUpdate', result);
            vm.isSaving = false;
          // vm.comentarioMostrado.push(result);
            vm.textoComentado=null;
           getComentario();
        }

        /**
         * @author Christopher Sullivan
         * @param result
         * @version 1.0
         */
        function onSaveError(result) {
            vm.isSaving = false;
            vm.success = null;
            if (result.status === 500) {
                vm.errorConnection = true;
                vm.error = 'ERROR';
            }else {
                vm.error = 'ERROR';
            }

        }

        /**
         * @author Christopher Sullivan
         * salva la respuesta y agrega la respuesta al comentario
         * @param comentario
         * @version 1.0
         */
        vm.salvarRespuesta = function (comentario) {

            if (vm.comentarioCopia == null) {
                vm.comentarioCopia = comentario;
            } else {
                if (vm.textoRespondido == undefined) {
                    vm.comentarioCopia = null;
                } else {
                    vm.respuesta.contenido = vm.textoRespondido;
                    //vm.respuesta.idComentarioRId=comentario.id;
                    vm.comentarioMostradoCopia = comentario;
                    comentario.respuesta = vm.textoRespondido;
                    Respuesta.save(vm.respuesta, onSaveSuccessR,onSaveError);
                    vm.comentarioCopia = null;
                }
            }
        }

        /**
         * @author Christopher Sullivan
         * @param result
         * @version 1.0
         */
        function onSaveSuccessR(result) {
            vm.comentarioMostradoCopia.idComentarioRespuestaId = result.id;
            Comentario.update(vm.comentarioMostradoCopia, onSaveSuccess, onSaveError);
            vm.textoRespondido=null;
            vm.escribirResp = false;


        }

        /**
         * @author Jose Nuñez
         * Envía la publicación seleccionada al visor para poder verla.
         * Utiliza un broadcast para envíar la publicación
         */
        vm.verPublicacion = function (publicacion) {
            var url = $state.href('verPublicacion', {id: publicacion.id, tipo: 2});
            $window.open(url, '_blank');
        }


    }
})();
