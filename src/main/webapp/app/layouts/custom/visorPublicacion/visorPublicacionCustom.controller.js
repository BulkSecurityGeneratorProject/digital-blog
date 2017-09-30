/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('VisorPublicacionCustomController', VisorPublicacionCustomController);

    VisorPublicacionCustomController.$inject = ['$scope','$timeout', '$state', '$q', 'VisorPublicacion', 'PublicacionServiceShare'];

    function VisorPublicacionCustomController($scope, $timeout, $state, $q, VisorPublicacion, PublicacionServiceShare) {
        var vm = this;
        // Variables

        vm.paginaContenido = "";
        vm.buttons = [];

        vm.capitulos = [];
        vm.capsTemporales = [];
        vm.paginas = [];
        vm.idCapitulos = [];
        vm.paginasFormato = [];

        vm.capituloActual = 0;
        vm.paginaActual = 0;
        vm.cantidadPaginas = 0;
        vm.paginaActualCapitulo = 0;
        vm.autor = {};

        vm.pagina = {};
        vm.actual = "";
        vm.tipoVisor = 0;
        tiny();


        /**
         * @author Jose Nuñez
         *  Function que se ejecuta apenas se llama al controlador, por medio de los parametros revisa que tipo es
         *  1: Es un capitulo: Que se utiliza para ver el cap anterior en el editor de text de publicación compartida
         *  2: Es la publicación entera, esto es para utilizarse para ver toda la publicación (Biblioteca general)
         *@version 1.0
         */
       function init () {
            //Si el tipo de publicacion es 1: Capitulo anterior. 2: Publicación entera
            if ($state.params.tipo == 2) {
                vm.tipoVisor = $state.params.tipo;
                obtenerPublicacion($state.params.id);
            } else {
                obtenerPaginas($state.params.id);
                vm.tipoVisor = $state.params.tipo;
            }
        };


        /**
         * @author Jose Nuñez
         * Funcion de tiny, en este caso se utiliza como visor. Al visor se le agregan los siguientes botones:
            1: Pagina anterior
            2: Pagina siguiente
            3: Ver pagina (Esto es un botón, pero es para mostrar la pagina actual y el total de paginas)
         *@version 1.0
         */
        function tiny() {
            vm.tinymceOptions = {
                mode: "textareas",
                readonly: true,
                selector: 'textarea',  // change this value according to your HTML
                height: '360',
                plugin_preview_width: 1140,
                menubar: false,
                statusbar: false,
                toolbar: [
                    'btnPaginaAnterior | btnPaginaSiguiente |  verPagina'
                ],
                plugins: [
                    "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker toc noneditable",
                    "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                    "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern preview"
                ],
                wordcount_countregex: '/[\w\u2019\x27\-\u00C0-\u1FFF]+/g',
                a_configuration_option: 400,
                setup: function (editor) {
                    editor.on('change', function (e) {
                        // if (vm.paginaActual == 0) {
                        //     vm.buttons['pagina-anterior'].disabled(true);
                        // } else {
                        //     vm.buttons['pagina-anterior'].disabled(false);
                        // }
                        //
                        // if (vm.paginaActual == vm.cantidadPaginas) {
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
                            vm.buttons['pagina-anterior'].disabled(true);
                            vm.buttons['editorUndoManager'] = editor.undoManager;
                            $('#mceu_0').removeClass("mce-disabled");
                            // $('#mceu_2').removeClass("mce-disabled");
                            $('#mceu_5').css("border-style", "none");
                            vm.buttons['back'] = $('#mceu_2');
                            vm.buttons['pagina-siguiente'].disabled(false);
                            vm.buttons['ver-pagina'].disabled(false);
                            vm.buttons['editor'] = editor;
                            init();
                        }),
                        editor.addButton('btnPaginaAnterior', {
                            text: '',
                            image: '../../content/images/left-arrow.svg',
                            tooltip: 'Página anterior',
                            onclick: function () {
                                if (vm.paginaActual === 0) {
                                    vm.buttons['pagina-anterior'].disabled(true);
                                } else {
                                    if ($state.params.tipo == 2) {
                                        vm.paginaActual = vm.paginaActual - 1;
                                        updateTextoEditor(vm.paginasFormato[vm.paginaActual], editor);
                                        vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                                        // vm.buttons['pagina-siguiente'].disabled(false);
                                        if (vm.paginaActual === 0) {
                                            vm.buttons['pagina-anterior'].disabled(true);
                                        }
                                        vm.buttons['pagina-siguiente'].disabled(false);
                                    } else {
                                        vm.paginaActual = vm.paginaActual - 1;
                                        vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                                        updateTextoEditor(vm.paginas[0][vm.paginaActual], vm.buttons['editor']);
                                        if(vm.paginaActual  === 0) {
                                            vm.buttons['pagina-anterior'].disabled(true);
                                        }
                                        vm.buttons['pagina-siguiente'].disabled(false);
                                    }
                                }
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
                                    if (vm.paginaActual === vm.cantidadPaginas || vm.cantidadPaginas === 1) {
                                        vm.buttons['pagina-siguiente'].disabled(true);
                                    } else {
                                        if ($state.params.tipo == 2) {
                                            vm.paginaActual = vm.paginaActual + 1;
                                            updateTextoEditor(vm.paginasFormato[vm.paginaActual], editor);
                                            vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                                            // vm.buttons['pagina-anterior'].disabled(false);
                                            if ((vm.paginaActual + 1) === vm.cantidadPaginas) {
                                                vm.buttons['pagina-siguiente'].disabled(true);
                                            }
                                            vm.buttons['pagina-anterior'].disabled(false);
                                        } else {
                                            vm.paginaActual = vm.paginaActual + 1;
                                            vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                                            updateTextoEditor(vm.paginas[0][vm.paginaActual], vm.buttons['editor']);
                                            if((vm.paginaActual + 1) === vm.cantidadPaginas) {
                                                vm.buttons['pagina-siguiente'].disabled(true);
                                            }
                                            vm.buttons['pagina-anterior'].disabled(false);
                                        }
                                    }
                                },
                                onPostRender: function () {
                                    vm.buttons['pagina-siguiente'] = this;
                                }
                            }
                        ),
                        editor.addButton('verPagina', {
                            text: 'Pagina: ' + vm.paginaActual + '/' + vm.cantidadPaginas,
                            image: '',
                            tooltip: '',
                            onPostRender: function () {
                                vm.buttons['ver-pagina'] = this;
                            }
                        })
                }
            };
        }


        /**
         * @author Jose Nuñez
         * Funcion para actualizar el contenido por pagina en el editor (visor).
         * @param pagina
         * @param editor
         * @version 1.0
         *
         */
        function updateTextoEditor(pagina, editor) {
            editor.getBody().innerHTML = pagina.contenido;
        }

        /**
         * @author Jose nuñez
         *  Función para obtener la publicación, por medio del id recibido.
            En esta función se llama a obtener usuario para obtener el autor de la publicación y tambien se llama
             a obtener capitulos
         * @param id
         * @version 1.0
         */
        function obtenerPublicacion(id) {
            VisorPublicacion.obtenerPublicacion({idPublicacion: id}).success(function (data) {
                vm.publicacion = data;
                VisorPublicacion.obtenerUsuarioById({idUsuario: vm.publicacion.usuarioId})
                    .then(function (data) {
                        vm.autor = data.data;
                        obtenerCapitulos();
                    })
            }).error(function (error) {
                console.log(error);
                console.log('Error al obtener publicación');
            });
        }


        /**
         * @author Jose nuñez
         * Esta función obtiene los capitulos por medio de la publicación (id),
           Se llama a obtener paginas por cada capitulo.
         * @version 1.0
         */
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


        /**
         * @author Jose nuñez
         * Se obtienen las paginas por idCapitulo, esta funcion se utiliza
           para ver el capitulo anterior de la publicacion compartida, a esta función se le pada el id del capitulo anterior.
         * @param id
         * @version 1.0
         */
        function obtenerPaginas(id) {
            VisorPublicacion.obtenerPaginas({idCapitulo: id}).success(function (data) {
                vm.paginas.push(data);
                updateTextoEditor(vm.paginas[0][vm.paginaActual], vm.buttons['editor']);
                vm.cantidadPaginas = vm.paginas[0].length;
                vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
            }).error(function (error) {
                console.log(error);
            });
        }



        /**
         * @author Jose nuñez
         * Funcion para popular el arreglo de paginas formato para poder ver paginas anterior y siguientes
         * Este recorre por los capitulos y pagina que pertenecen a cada capitulo
         * @version 1.0
         */
        function procesarPaginas() {
            for (var i = 0; i < vm.capitulos.length; i++) {
                for (var x = 0; x < vm.paginas.length; x++) { //Es una arreglo de 2 y cada objeto tiene 2 objetos dentro
                    for (var y = 0; y < vm.paginas[x].length; y++) { //Es una arreglo de 2 y cada objeto tiene 2 objetos dentro
                        if (vm.capitulos[i].id == vm.paginas[x][y].capituloId) {
                            if (vm.paginasFormato.indexOf(vm.paginas[x][y]) === -1) {
                                vm.paginasFormato.push(vm.paginas[x][y]);
                                ordenarPorId(vm.paginasFormato, 'id');
                                vm.cantidadPaginas = vm.paginasFormato.length;
                                //     updateTextoEditor(vm.paginasFormato[vm.paginaActual], vm.buttons['editor']);
                                //     vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
                            }
                        }
                    }
                }
            }
            $timeout(function () {
                updateTextoEditor(vm.paginasFormato[vm.paginaActual], vm.buttons['editor']);
                vm.buttons['ver-pagina'].text("Página: " + (vm.paginaActual + 1) + '/' + vm.cantidadPaginas);
            }, 1000);
            /**
             * @author Jose Nuñez
             * Como son id auto incrementales el menor siempre será la primera y así sucesivamente
             * Esta funcion ordena el array que se le pasa por medio de la propiedad ID
             * @version 1.0
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


    }

})
();
