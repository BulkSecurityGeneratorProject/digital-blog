/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('CrearPublicacionCompartidaEditorCustomController', CrearPublicacionCompartidaEditorCustomController);

    CrearPublicacionCompartidaEditorCustomController.$inject = ['$scope','$uibModal', '$timeout', '$rootScope', '$state', '$window', '$stateParams', 'PublicacionServiceShare', 'NotificacionGeneral', 'CompartidaServiceShare', 'CrearPublicacionCompartida', 'VisorPublicacion', 'CrearPublicacionIndividual'];

    function CrearPublicacionCompartidaEditorCustomController($scope,$uibModal, $timeout, $rootScope, $state, $window, $stateParams, PublicacionServiceShare, NotificacionGeneral, CompartidaServiceShare, CrearPublicacionCompartida, VisorPublicacion, CrearPublicacionIndividual) {
        var vm = this;
        vm.paginaContenido = "";
        vm.buttons = [];
        vm.paginasCapitulo = [];
        vm.contadorPaginas = 0;
        vm.paginaActual = 0;
        vm.publicacion = {};
        vm.capitulo = {};
        vm.pagina = {};
        vm.capituloAnterior = {};
        vm.paginasCapituloAnterior = [];


        /**
         * @author Jose Nuñez
         * Esta funcion obtiene el capitulo que le envian por medio de broadcast, para empezar la publicación compartida
         * @version 1.0
         */
        function obtenerCapitulo() {
            vm.capitulo = CompartidaServiceShare.result;
            //Si el numero de capitulo es mayor 1 se obtiene el capitulo anterior
            if (vm.capitulo.numeroCapitulo > 1) {
                CrearPublicacionIndividual.obtenerCapituloAnterior({
                    idPub: vm.capitulo.idPublicacionCId,
                    numeroCap: vm.capitulo.numeroCapitulo
                }).success(function (result) {
                    vm.capituloAnterior = result;
                }).error(function (error) {
                    console.error(error);
                    console.log('Error al obtener el capitulo anterior de la publiacion compartida');
                })
            }
        }

        obtenerCapitulo();
        tiny();

        /**
         * @author Jose Nuñez
         * Abre el editor para hacer la publicacion compartida
         * @version 1.0
         */
        function tiny() {
        vm.tinymceOptions = {
            selector: 'textarea',  // change this value according to your HTML
            height: '360',
            plugin_preview_width: 1140,
            menubar: false,
            statusbar: false,
            toolbar: [
                'undo redo | styleselect | link image | btnGuardar | btnNuevaPagina | btnPaginaAnterior | btnPaginaSiguiente | btnCapituloAnterior | btnTerminarParticipacion | preview',
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
                        vm.buttons['pagina-guardar'].disabled(true);
                        vm.buttons['pagina-nueva'].disabled(true);
                    } else {
                        vm.buttons['pagina-guardar'].disabled(false);
                    }
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
                        // vm.buttons['nuevo-capitulo'].disabled(true);

                        //Validamos si la persona pueda ver o no el capitulo anterior
                        // if(vm.capitulo.numeroCapitulo > 1){
                        //     vm.buttons['anterior-capitulo'].disabled(false);
                        // }else {
                        //     vm.buttons['anterior-capitulo'].disabled(true);
                        // }

                        if (vm.capituloAnterior === undefined) {
                            vm.buttons['anterior-capitulo'].disabled(true);
                        } else {
                            vm.buttons['anterior-capitulo'].disabled(false);
                        }
                    }),
                    editor.addButton('btnGuardar', {
                        text: '',
                        image: '../../content/images/save-icon.svg',
                        tooltip: 'Guardar',
                        onclick: function () {
                            var text = editor.getBody();
                            var cant = editor.getBody().text.length;
                            vm.pagina.contenido = editor.getBody().innerHTML;
                            if (vm.pagina.id == null) {
                                crearPagina();
                            } else {
                                actualizarPagina();
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
                            vm.buttons['pagina-nueva'].disabled(true);
                            crearNuevaPagina();
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
                    editor.addButton('btnTerminarParticipacion', {
                        text: '',
                        image: '../../content/images/notebook.svg',
                        tooltip: 'Terminar participación',
                        onclick: function () {
                            vm.showYesNo();
                        },
                        onPostRender: function () {
                            vm.buttons['nuevo-capitulo'] = this;
                        }
                    }),
                    editor.addButton('btnCapituloAnterior', {
                        text: '',
                        image: '../../content/images/open-book.svg',
                        tooltip: 'Ver capitulo anterior',
                        onclick: function () {
                            //Para ver el capitulo anterior es una pestaña aparte.
                            verCapituloAnterior();
                            // var url = $state.href('verPerfil', {id: 5});

                        },
                        onPostRender: function () {
                            vm.buttons['anterior-capitulo'] = this;
                        }
                    })
            }
        };
    }
        // vm.init();


        /**
         * @author Jose Nuñez
         * Crea el capitulo de la publicacion compartida
         * @version 1.0
         */
        function crearCapitulo() {
            CrearPublicacionIndividual.crearCapitulo({
                capitulo: vm.capitulo//con solo enviar publicacion se saca el numero de capitulo
            }).success(function (data) {
                console.log('todo bien capitulo');
                vm.capitulo = data;
                vm.pagina.capituloId = vm.capitulo.id;
                crearPagina();
            }).error(function (error) {
                console.log(error.message);
            });
        }
        /**
         * @author Jose Nuñez
         * Crea la pagina de la publicacion compartida
         * @version 1.0
         */
        function crearPagina() {
            vm.pagina.capituloId = vm.capitulo.id;
            CrearPublicacionIndividual.crearPagina({
                pagina: vm.pagina
            }).success(function (data) {
                var objPagina = {};
                console.log('todo bien pagina');
                vm.pagina = data;
                objPagina = data;
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['pagina-guardar'].disabled(false);
                vm.contadorPaginas = vm.contadorPaginas + 1;
                vm.paginaActual = vm.contadorPaginas;
                vm.paginasCapitulo[vm.contadorPaginas] = objPagina;
                if (vm.contadorPaginas > 1) {
                    vm.buttons['pagina-anterior'].disabled(false);
                }
                vm.buttons['nuevo-capitulo'].disabled(false);

            }).error(function (error) {
                vm.errorConnection = true;
                // console.log(error.message);
            });
        }


        /**
         * @author Jose Nuñez
         * Funcion para actualizar la pagina actual, valida si la pagina tiene id, si tiene hace update.
         * @version 1.0
         */
        function actualizarPagina() {
            CrearPublicacionIndividual.actualizarPagina({
                pagina: vm.pagina
            }).success(function (data) {
                vm.pagina = data;
                console.log('todo bien pagina actualizar');
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['pagina-guardar'].disabled(false);
            }).error(function (error) {
                vm.errorConnection = true;
                // console.log(error.message);
            });
        }



        /**
         * @author Jose Nuñez
         * Función para crear una nueva pagina, esto hace un nueov objeto pagina
         * @version 1.0
         */
        function crearNuevaPagina() {
            vm.buttons['editorTexto'].getBody().innerHTML = "";
            vm.buttons['editorUndoManager'].clear();
            vm.buttons['pagina-nueva'].disabled(true);
            vm.pagina = {};
        }


        /**
         * @author Jose Nuñez
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
        }



        /**
         * @author Jose Nuñez
         * Metodo para obtener pagina siguiente
         * @version 1.0
         */
        function verPaginaSiguiente() {
            vm.paginaActual = vm.paginaActual + 1;
            vm.pagina = vm.paginasCapitulo[vm.paginaActual];
            vm.buttons['editorTexto'].getBody().innerHTML = vm.pagina.contenido;
            vm.buttons['editorUndoManager'].clear();
            if (vm.paginaActual == vm.contadorPaginas) {
                vm.buttons['pagina-siguiente'].disabled(true);
                vm.buttons['pagina-nueva'].disabled(false);
                vm.buttons['nuevo-capitulo'].disabled(false);
            }
            vm.buttons['pagina-anterior'].disabled(false);
        }


        /**
         * @author Jose Nuñez
         * Esta función permite ver el capitulo anterior por medio del visor, se abre en una nueva ventana
         * @version 1.0
         */
        function verCapituloAnterior() {
            //TODO: A este método le enviamos un parametro de mas para ver si es una publicación entera, o si solo para ver el capitulo anterior: 1 capitulo anterior, 2 entera
            // PublicacionServiceShare.enviarPublicacion(vm.capituloAnterior, 1);
            var url = $state.href('verPublicacion', {id:vm.capituloAnterior.id,tipo:1});
            $window.open(url, '_blank');
        }



        /**
         * @author Jose Nuñez
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
         * @author Jose Nuñez
         * Función para mostrar el modal de SI O NO, de terminar participación compartida
         * @version 1.0
         */
        vm.showYesNo = function () {
            var modal = $uibModal.open({
                animation: vm.animationsEnabled,
                templateUrl: 'app/layouts/custom/crearPublicacionCompartida/modalConfiguracionCompartida.html',
                controller: 'ConfigCompartidaDialogController',
                controllerAs: 'vm'
            })
            $scope.$on('compartida', function () {
                var result = CompartidaServiceShare.result;
                console.log(result);
                CrearPublicacionCompartida.terminarParticipacionCapitulo({capitulo: vm.capitulo}).success(function (result) {
                    $state.go('listarPublicacionesPorSeccion');
                }).error(function (error) {
                    console.log(error);
                    console.log('Error al tratar de terminar participación en publicación compartida');
                });

            });
        };
    }

})();
