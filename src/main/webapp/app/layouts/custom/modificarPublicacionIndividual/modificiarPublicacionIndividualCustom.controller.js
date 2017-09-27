/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ModificarPublicacionCustomController', ModificarPublicacionCustomController);

    ModificarPublicacionCustomController.$inject = ['Nota','PersonalizarCuenta','Principal','NotaService','$timeout', '$state', '$scope', '$q', '$stateParams', 'VisorPublicacion', 'PublicacionServiceShare','CrearPublicacionIndividual','NotificacionGeneral'];

    function ModificarPublicacionCustomController(Nota,PersonalizarCuenta,Principal,NotaService,$timeout, $state, $scope, $q, $stateParams, VisorPublicacion, PublicacionServiceShare,CrearPublicacionIndividual,NotificacionGeneral) {

        var vm = this;

        //La publicación que se va a querer modificar llegar por broadcast
        // $scope.$on('publicacionBroadcast', function () {
        //     vm.publicacion = PublicacionServiceShare.publicacion;
        // });

        vm.init = function () {
            vm.publicacion = PublicacionServiceShare.publicacion;
            if (vm.publicacion.titulo === undefined) {
                $state.go('home');
            } else {

                obtenerPublicacion();
                tiny();
            }
        };
        vm.init();

        vm.actualizar = actualizar;
        vm.paginaContenido = "";
        vm.buttons = [];

        vm.capitulos = [];
        vm.capsTemporales = [];
        vm.paginas = [];
        vm.idCapitulos = [];
        vm.paginasFormato = [];

        vm.capituloActual = 0;
        vm.paginaActual = 0;
        vm.capitulo = {};
        vm.cantidadPaginas = 0;
        vm.contadorPaginas = 0;
        vm.paginaActualCapitulo = 0;
        vm.autor = {};

        vm.pagina = {};
        vm.actual = "";

        function tiny() {
            vm.tinymceOptions = {
                selector: 'textarea',  // change this value according to your HTML
                height: '360',
                plugin_preview_width: 1140,
                menubar: false,
                statusbar: false,
                toolbar: [
                    'undo redo | styleselect | link image | btnGuardar | btnNuevaPagina | btnPaginaAnterior | btnPaginaSiguiente | btnNuevoCapitulo | preview',
                    'forecolor backcolor | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect | verPagina'
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
                        // if (vm.paginaActual == 1) {
                        //     vm.buttons['pagina-anterior'].disabled(true);
                        // } else {
                        //     vm.buttons['pagina-anterior'].disabled(false);
                        // }
                        // //
                        // if (vm.paginaActual == vm.contadorPaginas) {
                        //     vm.buttons['pagina-siguiente'].disabled(true);
                        // } else {
                        //     vm.buttons['pagina-anterior'].disabled(false);
                        // }
                        //
                        // if (vm.paginaActual === vm.cantidadPaginas) {
                        //     vm.capituloActual = vm.capituloActual + 1;
                        // }
                    }),
                        editor.on('init', function (e) {
                            vm.buttons['editorUndoManager'] = editor.undoManager;
                            vm.buttons['editorUndoManager'] = editor.undoManager;
                            vm.buttons['editorTexto'] = editor;
                            vm.buttons['nuevo-capitulo'].disabled(true);
                            vm.buttons['pagina-nueva'].disabled(true);
                            vm.buttons['pagina-anterior'].disabled(true);
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
                                console.log(vm.paginaActual);
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
                        }),
                        editor.addButton('verPagina', {
                            text: 'Pagina: ' + vm.paginaActual + '/' + vm.contadorPaginas,
                            image: '',
                            tooltip: '',
                            onPostRender: function () {
                                vm.buttons['ver-pagina'] = this;
                            }
                        })
                }
            };
        }

        //Empiezan métodos para actualizar publicación individual
        function crearPublicacionIndividual() {
            CrearPublicacionIndividual.crearPublicacionIndividual({
                publicacion: vm.publicacion//con solo enviar publicacion se saca el numero de capitulo
            }).success(function (data) {
                console.log('todo bien publicacion');
                vm.publicacion = data;
                vm.capitulo.idPublicacionCId = vm.publicacion.id;
                vm.buttons['pagina-guardar'].disabled(true);
                crearCapitulo();
            }).error(function (error) {
                console.log(error.message);
            });
        }

        function crearCapitulo() {
            CrearPublicacionIndividual.crearCapitulo({
                capitulo: vm.capitulo
            }).success(function (data) {
                console.log('todo bien capitulo');
                vm.capitulo = data;
                vm.pagina.capituloId = vm.capitulo.id;
                crearNuevaPagina();
                crearPagina();
            }).error(function (error) {
                console.log(error.message);
            });
        }

        //Metodo donde el capitulo le quita el id y limpia el objeto pagina
        function crearNuevoCapitulo() {
            vm.buttons['editorTexto'].getBody().innerHTML = "";
            vm.buttons['editorUndoManager'].clear();
            vm.buttons['nuevo-capitulo'].disabled(true);
            var idCapitulo = vm.pagina.capituloId;
            var capitulo = vm.capitulos.find(x.id === idCapitulo);
            vm.capitulo.idPublicacionCId = capitulo.idPublicacionCId;

            crearCapitulo();
            // vm.pagina = {};
            // vm.pagina.capituloId = vm.idCapitulo;
            vm.buttons['nuevo-capitulo'].disabled(true);
            //vm.buttons['pagina-anterior'].disabled(true);
        }

        function crearPagina() {
            //vm.pagina.capituloId = vm.capitulo.id;
            CrearPublicacionIndividual.crearPagina({
                pagina: vm.pagina
            }).success(function (data) {
                guardarNota(data);
                console.log('todo bien pagina');
                vm.pagina = data;
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['pagina-guardar'].disabled(false);
                vm.contadorPaginas = vm.contadorPaginas + 1;
                vm.paginaActual = vm.contadorPaginas;
                if (vm.contadorPaginas > 1) {
                    vm.buttons['pagina-anterior'].disabled(false);
                }
                vm.buttons['nuevo-capitulo'].disabled(false);

            }).error(function (error) {
                console.log(error.message);
            });

            ;
        }

        //Metodos para actualizar pagina
        function actualizarPagina() {
            CrearPublicacionIndividual.actualizarPagina({
                pagina: vm.pagina
            }).success(function (data) {
                guardarNota(data);
                vm.pagina = data;
                console.log(vm.paginaActual);
                console.log('todo bien pagina actualizar');
                if (vm.paginaActual !== 0) {
                    vm.buttons['pagina-nueva'].disabled(false);
                } else {
                    vm.buttons['pagina-nueva'].disabled(true);
                }
                vm.buttons['pagina-guardar'].disabled(false);


            }).error(function (error) {
                console.log(error.message);
            });
        }

        function crearNuevaPagina() {
            vm.buttons['editorTexto'].getBody().innerHTML = "";
            vm.buttons['editorUndoManager'].clear();
            vm.buttons['pagina-nueva'].disabled(true);
            var idCapitulo = vm.pagina.capituloId;
            vm.pagina = {};
            vm.pagina.capituloId = idCapitulo;
        }

        //Metodo para obtener pagina anterior
        function verPaginaAnterior() {
            if (vm.paginaActual === 0) {
                vm.buttons['pagina-anterior'].disabled(true);
            } else {
                vm.paginaActual = vm.paginaActual - 1;
                var editor = vm.buttons['editorTexto'];
                updateTextoEditor(vm.paginasFormato[vm.paginaActual], editor);
                vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.contadorPaginas);
                // vm.buttons['pagina-siguiente'].disabled(false);
                if (vm.paginaActual === 0) {
                    vm.buttons['pagina-anterior'].disabled(true);
                }
                vm.buttons['pagina-siguiente'].disabled(false);
            }
            //getNotas(vm.pagina.id);
        }

        //Metodo para obtener pagina siguiente
        function verPaginaSiguiente() {

            if (vm.paginaActual === vm.contadorPaginas || vm.contadorPaginas === 1) {
                vm.buttons['pagina-siguiente'].disabled(true);
            } else {
                vm.paginaActual = vm.paginaActual + 1;
                var editor = vm.buttons['editorTexto'];
                updateTextoEditor(vm.paginasFormato[vm.paginaActual], editor);
                vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.contadorPaginas);
                // vm.buttons['pagina-anterior'].disabled(false);
                if ((vm.paginaActual + 1) === vm.contadorPaginas) {
                    vm.buttons['pagina-siguiente'].disabled(true);
                }
                vm.buttons['pagina-anterior'].disabled(false);
            }
            //getNotas(vm.pagina.id);
        }

        function publicar() {
            vm.publicacion.estado = 2;
            if (vm.publicacion.id == null) {
                crearPublicacionIndividual();
            } else {
                CrearPublicacionIndividual.publicar({
                    publicacion: vm.publicacion
                }).success(function (data) {
                    NotificacionGeneral.notificacion('Felicidades', 'Su publicación ha sido publicada', 1);
                    $timeout(function () {
                        $state.go('home')
                    }, 5000);
                }).error(function (error) {
                    console.log(error.message);
                });
            }
        }

        //El editor obtiene el contenido de la primera pagina a editor
        function updateTextoEditor(pagina, editor) {
            editor.getBody().innerHTML = pagina.contenido;
            getNotas(pagina.id);
        }

        //Obtener una publicación
        function obtenerPublicacion() {
            VisorPublicacion.obtenerPublicacion({idPublicacion: vm.publicacion.id}).success(function (data) {
                vm.publicacion = data;
                VisorPublicacion.obtenerUsuarioById({idUsuario: vm.publicacion.usuarioId})
                    .then(function (data) {
                        vm.autor = data.data;
                        obtenerCapitulos();
                    })
            }).error(function (error) {
                console.log(error);
            });
        }

        function obtenerCapitulos() {
            VisorPublicacion.obtenerCapitulos({idPublicacion: vm.publicacion.id}).success(function (data) {
                vm.capitulos = data;
                vm.capsTemporales = data;
                vm.capituloActual = vm.capitulos[0].id;
                VisorPublicacion.obtenerUsuarioById({idUsuario: vm.publicacion.usuarioId})
                    .then(function (data) {
                        vm.autor = data.data;
                    });
                var arrayProm = [];
                for (var i = 0; i < vm.capitulos.length; i++) {
                    var prom = VisorPublicacion.obtenerPaginas({idCapitulo: vm.capitulos[i].id});
                    arrayProm.push(prom);
                }
                $q.all(arrayProm).then(function (values) {
                    for (var i = 0; i < vm.capitulos.length; i++) {
                        vm.paginas.push(values[i].data);
                    }
                    procesarPaginas();
                });


            }).error(function (error) {
                console.log(error);
            });
        }

        function obtenerPaginas(id) {
            VisorPublicacion.obtenerPaginas({idCapitulo: id}).success(function (data) {
                vm.paginas.push(data);

            }).error(function (error) {
                console.log(error);
            });
        }

        function procesarPaginas() {
            for (var i = 0; i < vm.capitulos.length; i++) {
                for (var x = 0; x < vm.paginas.length; x++) { //Es una arreglo de 2 y cada objeto tiene 2 objetos dentro
                    for (var y = 0; y < vm.paginas[x].length; y++) { //Es una arreglo de 2 y cada objeto tiene 2 objetos dentro
                        if (vm.capitulos[i].id == vm.paginas[x][y].capituloId) {
                            if (vm.paginasFormato.indexOf(vm.paginas[x][y]) === -1) {
                                vm.paginasFormato.push(vm.paginas[x][y]);
                                ordenarPorId(vm.paginasFormato, 'id');
                                vm.contadorPaginas = vm.paginasFormato.length;

                                //     updateTextoEditor(vm.paginasFormato[vm.paginaActual], vm.buttons['editor']);
                                //     vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                            }
                        }
                    }
                }
            }
            $timeout(function () {
                updateTextoEditor(vm.paginasFormato[vm.paginaActual], vm.buttons['editorTexto']);
                vm.pagina = vm.paginasFormato[vm.paginaActual];
                vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.contadorPaginas);
            }, 1000);
            /**
             * Como son id auto incrementales el menor siempre será la primera y así sucesivamente
             * */
            function ordenarPorId(array, prop) {
                array.sort(
                    function (a, b) {
                        if (a[prop] < b[prop]) {
                            return -1;
                        } else if (a[prop] > b[prop]) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                );
            }

        }

        /**
         * @author José Nuñez
         * Funcion que actualiza la publicación (Redirecciona a biblioteca personal)
         * @version 1.0
         */
        function actualizar() {
            publicar();
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
                console.log("Problema inesperado al traer el usuario con el id de JHIuser error:" + error);
            });
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
            console.log(result);
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

})
();
