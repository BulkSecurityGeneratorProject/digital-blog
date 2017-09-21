/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CrearPublicacionIndividualEditorCustomController', CrearPublicacionIndividualEditorCustomController);

    CrearPublicacionIndividualEditorCustomController.$inject = ['NotaService','Nota','Principal','PersonalizarCuenta','$timeout','$rootScope', '$state', '$scope', '$stateParams', 'PublicacionServiceShare', 'CrearPublicacionIndividual','NotificacionGeneral'];

    function CrearPublicacionIndividualEditorCustomController(NotaService,Nota,Principal,PersonalizarCuenta,$timeout,$rootScope, $state, $scope, $stateParams, PublicacionServiceShare, CrearPublicacionIndividual,NotificacionGeneral) {
        var vm = this;


        vm.paginaContenido = "";
        vm.buttons = [];
        vm.paginasCapitulo = [];
        vm.contadorPaginas = 0;
        vm.paginaActual = 0;
        vm.publicar = publicar;

        vm.publicacion = {};
        vm.capitulo = {
            id: null,
            numeroCapitulo: null,
            capituloId: null,
            idPublicacionCId: null
        };
        vm.pagina = {};

        vm.init = function () {
            $scope.$on('publicacionBroadcast', function () {
                console.log(PublicacionServiceShare.publicacion);
            });
            vm.publicacion = PublicacionServiceShare.publicacion;
            console.log(PublicacionServiceShare.publicacion);
             // if(vm.publicacion.titulo === undefined){
             //     $state.go('crearPublicacionIndividual');
             // }
        };

        /**
         * @author José Nuñez
         * Opciones para el editor de la publicacion
         * @type {{selector: string, height: string, plugin_preview_width: number, menubar: boolean, statusbar: boolean, toolbar: [*], plugins: [*], wordcount_countregex: string, a_configuration_option: number, setup: CrearPublicacionIndividualEditorCustomController.tinymceOptions.setup}}
         * @version 1.0
         */


        vm.tinymceOptions = {
            selector: 'textarea',  // change this value according to your HTML
            height: '360',
            plugin_preview_width: 1140,
            menubar: false,
            statusbar: false,
            toolbar: [
                'undo redo | styleselect | link image | btnGuardar | btnNuevaPagina | btnPaginaAnterior | btnPaginaSiguiente | btnNuevoCapitulo | preview',
                'forecolor backcolor | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect'
            ],
            plugins: [
                "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker toc",
                "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern preview"
            ],
            wordcount_countregex: '/[\w\u2019\x27\-\u00C0-\u1FFF]+/g',
            a_configuration_option: 400,
            setup: function (editor) {

                editor.on('change', function (e) {
                    if (editor.getBody().textContent.length == 0) {
                        //     vm.buttons['pagina-anterior'].disabled(true);
                        vm.buttons['pagina-guardar'].disabled(true);
                        vm.buttons['pagina-nueva'].disabled(true);
                    } else {
                        //  vm.buttons['pagina-anterior'].disabled(false);
                        vm.buttons['pagina-guardar'].disabled(false);
                   //     vm.buttons['pagina-nueva'].disabled(false);
                    }

               /*    vm.paginaActual = vm.pagina.numeroPagina;
                    if (vm.pagina.numeroPagina == 1 || vm.pagina.numeroPagina == undefined ) {
                        vm.buttons['pagina-anterior'].disabled(true);
                    } else {
                        vm.buttons['pagina-anterior'].disabled(false);
                    }
*/
                }),
                    editor.on('init', function (e) {
                        /*Primero valida si la pagina actual tiene id 1 o es null, significa que no tiene pagina
                         anterior, tambien tiene que validar si capitulo es null o es el 1
                         */
                        vm.buttons['pagina-anterior'].disabled(true);
                        vm.buttons['editorUndoManager'] = editor.undoManager;
                        vm.buttons['editorTexto'] = editor;
                        vm.buttons['pagina-nueva'].disabled(true);
                        vm.buttons['pagina-siguiente'].disabled(true);
                        vm.buttons['nuevo-capitulo'].disabled(true);
                    }),
                    editor.addButton('btnGuardar', {
                        text: '',
                        image: '../../content/images/save-icon.svg',
                        tooltip: 'Guardar',
                        onclick: function () {
                            var text = editor.getBody();
                            var cant = editor.getBody().text.length;
                            vm.buttons['pagina-guardar'].disabled(true);
                            vm.pagina.contenido = editor.getBody().innerHTML;
                            if (vm.publicacion.id == null) {
                                crearPublicacionIndividual();
                            } else {
                                if (vm.pagina.id == null) {
                                    crearPagina();
                                } else {
                                    actualizarPagina();
                                }
                            }
                        },
                        onPostRender: function () {
                            vm.buttons['pagina-guardar'] = this;
                        }
                    }),
                    editor.addButton('btnNuevaPagina', {
                        text: '',
                        image: '../../content/images/add-new-document.svg',
                        tooltip: 'Nueva página',
                        onclick: function () {
                            vm.pagina.contenido = editor.getBody().innerHTML;
                            if (vm.publicacion.id == null) {
                                crearPublicacionIndividual();
                            } else {
                                vm.buttons['pagina-nueva'].disabled(true);
                                crearNuevaPagina();
                            }
                        },
                        onPostRender: function () {
                            vm.buttons['pagina-nueva'] = this;
                        }
                    }),
                    editor.addButton('btnPaginaAnterior', {
                        text: '',
                        image: '../../content/images/left-arrow.svg',
                        tooltip: 'Página anterior',
                        onclick: function () {
                           verPaginaAnterior();
                        },
                        onPostRender: function () {
                            vm.buttons['pagina-anterior'] = this;
                        }
                    }),
                    editor.addButton('btnPaginaSiguiente', {
                        text: '',
                        image: '../../content/images/right-arrow.svg',
                        tooltip: 'Página siguiente',
                        onclick: function () {
                            verPaginaSiguiente();
                        },
                        onPostRender: function () {
                            vm.buttons['pagina-siguiente'] = this;
                        }
                    }),
                    editor.addButton('btnNuevoCapitulo', {
                        text: '',
                        image: '../../content/images/open-book.svg',
                        tooltip: 'Nuevo capitulo',
                        onclick: function () {
                            crearNuevoCapitulo();
                        },
                        onPostRender: function () {
                            vm.buttons['nuevo-capitulo'] = this;
                        }
                    })
            }
        };
        vm.init();


        /**
         * @author José Nuñez
         * Metodos para crear publicacion individual
         * @version 1.0
         */
        function crearPublicacionIndividual() {
            CrearPublicacionIndividual.crearPublicacionIndividual({
                publicacion: vm.publicacion//con solo enviar publicacion se saca el numero de capitulo
            }).success(function (data) {
                vm.publicacion = data;
                vm.capitulo.idPublicacionCId = vm.publicacion.id;
                vm.buttons['pagina-guardar'].disabled(true);
                crearCapitulo();
            }).error(function (error) {
                vm.errorConnection = true;
                // console.log(error.message);
            });
            vm.errorConnection = false;
        }


        /**
         * @author José Nuñez
         * Funcion que crea capitulos de la publicacion
         * @version 1.0
         */
        function crearCapitulo() {
            CrearPublicacionIndividual.crearCapitulo({
                capitulo: vm.capitulo//con solo enviar publicacion se saca el numero de capitulo
            }).success(function (data) {
                vm.capitulo = data;
                vm.pagina.capituloId = vm.capitulo.id;
                crearPagina();
            }).error(function (error) {
                console.log(error.message);
            });
        }

        /**
         * @author José Nuñez
         * Funcion que crea paginas con el capitulo en el que está
         * @version 1.0
         */
        function crearPagina() {
            vm.pagina.capituloId = vm.capitulo.id;
            CrearPublicacionIndividual.crearPagina({
                pagina: vm.pagina
            }).success(function (data) {
                guardarNota(data);
                var objPagina = {};
                vm.pagina = data;
                objPagina = data;
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['pagina-guardar'].disabled(false);
                vm.contadorPaginas = vm.contadorPaginas + 1;
                vm.paginaActual = vm.contadorPaginas;
                vm.paginasCapitulo[vm.contadorPaginas] = objPagina;
                if(vm.contadorPaginas > 1){
                    vm.buttons['pagina-anterior'].disabled(false);
                }
                vm.buttons['nuevo-capitulo'].disabled(false);

            }).error(function (error) {
                console.log(error.message);
            });

            ;
        }



        /**
         * @author José Nuñez
         * Metodos para actualizar pagina
         * @version 1.0
         */
        function actualizarPagina() {
            CrearPublicacionIndividual.actualizarPagina({
                pagina: vm.pagina
            }).success(function (data) {
                guardarNota(data);
                vm.pagina = data;
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['pagina-guardar'].disabled(false);
            }).error(function (error) {
                console.log(error.message);
            });
        }
        /**
         * @author José Nuñez
         * Funcion que crea un nuevo objeto de pagina
         * @version 1.0
         */
        function crearNuevaPagina() {
            vm.buttons['editorTexto'].getBody().innerHTML = "";
            vm.buttons['editorUndoManager'].clear();
            vm.buttons['pagina-nueva'].disabled(true);
            vm.pagina = {};
            vm.notaEscrito=[];
        }


        /**
         * @author José Nuñez
         * Metodo para obtener pagina anterior
         * @version 1.0
         */
        function verPaginaAnterior() {
            vm.paginaActual = vm.paginaActual - 1;
            vm.pagina = vm.paginasCapitulo[vm.paginaActual];
            vm.buttons['editorTexto'].getBody().innerHTML = vm.pagina.contenido;
            vm.buttons['editorUndoManager'].clear();
            if (vm.paginaActual == 1) {
                vm.buttons['pagina-anterior'].disabled(true);
            }
            vm.buttons['pagina-siguiente'].disabled(false);
            vm.buttons['pagina-nueva'].disabled(true);
            vm.buttons['nuevo-capitulo'].disabled(true);
            getNotas(vm.pagina.id);
        }



        /**
         * @author José Nuñez
         * Metodo para obtener pagina siguiente
         * @version 1.0
         */
        function verPaginaSiguiente() {
            vm.paginaActual = vm.paginaActual + 1 ;
            vm.pagina = vm.paginasCapitulo[vm.paginaActual];
            vm.buttons['editorTexto'].getBody().innerHTML = vm.pagina.contenido;
            vm.buttons['editorUndoManager'].clear();
            if (vm.paginaActual == vm.contadorPaginas) {
                vm.buttons['pagina-siguiente'].disabled(true);
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['nuevo-capitulo'].disabled(false);
            }
            vm.buttons['pagina-anterior'].disabled(false);
            getNotas(vm.pagina.id);
        }


        /**
         * @author José Nuñez
         * Metodo donde el capitulo le quita el id y limpia el objeto pagina
         * @version 1.0
         */
        function crearNuevoCapitulo() {
            vm.buttons['editorTexto'].getBody().innerHTML = "";
            vm.buttons['editorUndoManager'].clear();
            vm.buttons['nuevo-capitulo'].disabled(true);
            vm.capitulo.id = null;
            crearCapitulo();
            vm.pagina = {};
            //vm.pagina.contenido = editor.getBody().innerHTML;
            vm.buttons['nuevo-capitulo'].disabled(true);
            vm.buttons['pagina-anterior'].disabled(true);
        }


        /**
         * @author José Nuñez
         * Funcion que le cambia el estado a publicada a la publicación individual (2).
         * @version 1.0
         */
        function publicar(){
            vm.publicacion.estado = 2;
            if(vm.publicacion.id == null){
                crearPublicacionIndividual();
            }else {
                CrearPublicacionIndividual.publicar({
                    publicacion: vm.publicacion
                }).success(function (data) {
                    NotificacionGeneral.notificacion('Felicidades', 'Su publicación ha sido publicada', 1);
                    $timeout(function () {
                        $state.go('home')
                    }, 1000);
                }).error(function (error) {
                    console.log(error.message);
                });
            }
        }

        vm.textoComentado;
        vm.usuarioComentando;
        vm.usuario;
        vm.notaEscrito=[];
        vm.notaEscritoCopia=[];
        vm.textoRespondido;
        vm.escribirResp=false;
        vm.notaCopia=null;
        vm.errorConnection = false;

        vm.nota={
            id: null,
            contenido: null,
            paginaNotaId:null,
        };

        Principal.identity().then(function(account) {
            vm.usuario = account;
            obtenerUsuarioNormal()
        });

        function obtenerUsuarioNormal(){
            PersonalizarCuenta.obtenerUsuarioNormal(vm.usuario.id).success(function(response){
                vm.usuarioComentando=response;
            }).error(function (error){
                vm.errorConnection = true;
            });
            vm.errorConnection = false;
        }

        /**
         * @author Christopher Sullivan
         * agrega la nueva nota al arreglo de notas
         * @version 1.0
         */
        vm.salvarComentario =function() {
            if(vm.textoComentado != undefined){
                vm.isSaving = true;
                vm.nota.contenido=vm.textoComentado;
                vm.notaCopia=Object.assign({},vm.nota);
                vm.notaEscrito.push(vm.notaCopia);
                vm.notaCopia= new Object();
                vm.textoComentado=null;
            }
        }
        /**
         * @author Christopher Sullivan
         * guarda la nota a la base de datos
         * @param data
         */
        function guardarNota(data){
            for (var i=0;i<vm.notaEscrito.length;i++){
                if( vm.notaEscrito[i].id==null){
                    vm.notaEscrito[i].paginaNotaId=data.id;
                    vm.notaGuardada= Nota.save(vm.notaEscrito[i],onSaveSuccessN,onSaveErrorN)
                    vm.notaEscrito[i]=vm.notaGuardada;
                }
            }
        }

        /**
         * @author Christopher Sullivan
         * se trae a tosa las notas que tengan el id de la página
         * @param idpagina
         * @version 1.0
         */
        function getNotas(idpagina) {
            NotaService.getNotaPagina({idPagina: idpagina}).success(function (responce){
                vm.notaEscrito=responce;
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

        /**
         * @author Christopher Sullivan
         * borra a la nota
         * @param nota
         * @version 1.0
         */
        vm.borrarNota= function (nota){
            if(nota.id != null || nota.id != undefined){
                Nota.delete({id: nota.id},onSaveSuccessN,onSaveErrorN);
            }
                var index = vm.notaEscrito.indexOf(nota);
                vm.notaEscrito.splice(index, 1);
        }
        function onSaveSuccessN(result) {
            vm.errorConnection = false;
        }
        function onSaveErrorN(result) {
            vm.isSaving = false;
            vm.success = null;
            if (result.status === 500) {
                vm.errorConnection = true;
                vm.error = 'ERROR';
            }else {
                vm.error = 'ERROR';
            }

        }

    }

})();
