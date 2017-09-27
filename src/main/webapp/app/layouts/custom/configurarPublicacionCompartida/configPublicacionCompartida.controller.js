/**
 * Created by Maureen on 4/4/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('configPublicacionCompartidaController', configPublicacionCompartidaController);

    configPublicacionCompartidaController.$inject = ['$uibModal', '$scope', 'DataUtils', 'Publicacion', 'PersonalizarCuenta', 'Categoria', 'TemaCustomService', 'Principal', 'ListarSeguidores', '$state', 'ConfigurarPublicacionCompartida', 'CompartidaServiceShare'];

    function configPublicacionCompartidaController($uibModal, $scope, DataUtils, Publicacion, PersonalizarCuenta, Categoria, TemaCustomService, Principal, ListarSeguidores, $state, ConfigurarPublicacionCompartida, CompartidaServiceShare) {
        var vm = this;
        vm.publicacion = {};
        vm.listaTema = [];
        vm.tema = {};
        vm.elegidos = [];
        vm.segudires = [];
        vm.capitulos = [];
        vm.verificar = false;
        vm.mostrarCol = false;
        vm.agregar = agregarTemas;
        vm.completeTema = vm.completeTema;
        rellenarListTema();
        vm.errorCol = false;

        /**
         * @author Maureen Leon
         * Obtiene al usuario logueado
         * @version 1.0
         */
        Principal.identity().then(function (account) {
            vm.cuentaActual = account;
            vm.idUsuarioActual = vm.cuentaActual.id;
            obtenerUsuarioNormal();
        });
        Categoria.query(function (result) {
            vm.categorias = result;
            vm.searchQuery = null;
        });

        /**
         * @author Maureen Leon
         * Setea y genera una vista previa de la imagen seleccionada para la publicacion
         * @version 1.0
         */
        vm.setFotopublicacion = function ($file, publicacion) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        publicacion.fotopublicacion = base64Data;
                        publicacion.fotopublicacionContentType = $file.type;
                    });
                });
            }
        };

        /**
         * @author Maureen Leon
         * Obtiene al usuario normal a partir del JHIUSER del usuario logueado
         * @version 1.0
         */
        function obtenerUsuarioNormal() {
            Principal.identity().then(function (account) {
                vm.account = account;
                PersonalizarCuenta.obtenerUsuarioNormal(account.id).success(function (response) {
                    vm.usuario = response;
                    obtenerSeguidores(vm.usuario.id);
                }).error(function (error) {
                    console.log("Problema obtenerUsuarioNormal");
                });
            });
        }

        /**
         * @author Maureen Leon
         * Obtiene una lista de los seguidores y para invitarlos a colaborar en la publicacion compartida
         * @version 1.0
         */
        function obtenerSeguidores() {
            ListarSeguidores.obtenerSeguidores(vm.usuario.id).success(function (response) {
                vm.seguidores = response;
            }).error(function (error) {
                console.log("Problema inesperado al traer seguidores de un usuario en controller de asignar colaboradores");
            });
        }

        /**
         * @author Maureen Leon
         * Agrega a la lista de colaboradores un colaborador seleccionado
         * @version 1.0
         */
        vm.elegir = function (usuario) {
            vm.elegidos.push(usuario);
            var index = vm.seguidores.indexOf(usuario);
            vm.seguidores.splice(index, 1);
        }
        /**
         * @author Maureen Leon
         * remueve de la lista de colaboradores un colaborador que se desea quitar
         * @version 1.0
         */
        vm.pop = function (usuario) {
            vm.seguidores.push(usuario);
            var index = vm.elegidos.indexOf(usuario);
            vm.elegidos.splice(index, 1);
        }
        /**
         *@author Maureen Leon
         * @version 1.0
         */
        vm.asignarColaboradores = function () {
            vm.mostrarCol = true;
        }
        /**
         @author Maureen Leon
          @version 1.0
         */
        vm.volver = function () {
            vm.mostrarCol = false;

        }

        /**
         * @author José Nuñez
         * @version 1.0
         * Crea una publicacion con estado 1 que significa sin publicar
         */
        vm.crearPublicacion = function () { // se crea la publicacion

            vm.publicacion.usuarioId = vm.usuario.id;
            if (vm.elegidos.length == 0) {
                vm.errorCol = true;
            } else {
                vm.errorCol = false;
                vm.publicacion.estado = 1;
                ConfigurarPublicacionCompartida.configPublicacionCompartida({publicacion: vm.publicacion}).success(function (response) {
                    vm.publicacion = response;
                    crearCapitulos();
                }).error(function (error) {
                    console.log('error al crear publicacion compartida')
                });
            }

        }
        /**
         * @author José Nuñez
         * Crear un capitulo por cada usuario asignado ,se crean los capitulos ocn el id de la publicacion
         //SE DEBE CREAR UN USUARIO MAS YA Q EL USUARIO LOGUEADO ES UN COLABORADOR DE LA PUBLICACION
         *@version 1.o
         */
        function crearCapitulos() {
            var iteraciones = vm.publicacion.cantidadIteraciones * (vm.elegidos.length + 1);
            var capitulos = [];
            for (var i = 0; i < iteraciones; i++) {
                var capitulo = {
                    idPublicacionCId: vm.publicacion.id
                }
                capitulos.push(capitulo);
            }
            ConfigurarPublicacionCompartida.crearCapitulos({capitulo: capitulos}).success(function (response) {
                vm.capitulos = response;
                asignarElegidos();
            }).error(function (error) {
                console.log('error al crear capitulos compartida')
            });
        }

        /**
         * @author Maureen Leon
         * Asigna los colaboradores en forma rotativa a cada capitulo , colaboradoresList tiene los elegidos mas el usuario logueado q tambien es participante
         */
        function asignarElegidos() {
            var colaboradoresList = [];
            vm.colaboraciones = []
            colaboradoresList.push(vm.usuario);
            for (var i = 0; i < vm.elegidos.length; i++) {
                colaboradoresList.push(vm.elegidos[i]);
            }
            console.log(colaboradoresList);
            var indiceColaborador = 0;
            for (var i = 0; i < vm.capitulos.length; i++) {
                if (indiceColaborador >= colaboradoresList.length) {
                    indiceColaborador = 0;
                }
                var colaborador = {
                    capituloCId: vm.capitulos[i].id,
                    publicacionId: vm.publicacion.id,
                    idUsuarioId: colaboradoresList[indiceColaborador].id
                }
                indiceColaborador++;
                vm.colaboraciones.push(colaborador);
            }
            ConfigurarPublicacionCompartida.registrarColaboradores({colaboradores: vm.colaboraciones}).success(function (response) {
                console.log(response);
                $state.go('home');
            }).error(function (error) {
                console.log('error al crear colaboradores')
            });
        }


        /**
         * @author Eduardo Guerrero
         * Actualiza constantemente la lista del autocompletar
         * @version 1.0
         */
        vm.updateTemas = function () {
            vm.temass = vm.listaTema;
        }

        /**
         * @author Eduardo Guerrero
         * Obtiene el id del tema y se refleja el contenido del input
         * @param suggestion obtiene el valor de lo seleccionado
         * @version 1.0
         */
        vm.obtenerIdTema = function (suggestion) {
            TemaCustomService.listarTemas().success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    if (suggestion == data[i].nombre) {
                        vm.publicacion.temaId = data[i].id;
                    }
                }
                console.log("Suggestion selected: " + suggestion + " id: " + vm.idTema);
            })
        }

        /**
         * @author Eduardo Guerrero
         * Verifica que la palabra ingresada no este repetido
         * @param tema lo recibe desde el completar
         * return un boolean true si esta ya en la lista, false si no esta
         * @version 1.0
         */
        function verificarTema(tema) {
            for (var i = 0; i < vm.listaTema.length; i++) {
                if (tema == vm.listaTema[i]) {
                    vm.verificar = true;
                    break;
                } else {
                    vm.verificar = false;
                }
            }
            return vm.verificar
        }

        /**
         * @author Eduardo Guerrero
         * Permite agregar temas que no esten dentro de los temas predefinidos
         * Siempre modifica el texto poniendo la primera letra en mayuscula y lo demas en minuscula
         * @version 1.0
         */
        function agregarTemas() {
            //Transforma el texto con la primera letra en mayuscula
            //y el resto en miniscula.
            vm.tema = {nombre: vm.completeTema};
            var texto = vm.tema.nombre;
            var primero = texto.charAt(0);
            var resultado = primero.toUpperCase() + texto.substring(1, texto.length).toLowerCase()
            if
            (verificarTema(resultado) == true) {
                vm.verificar = false;
            } else {
                TemaCustomService.agregarTema({type: vm.tema}).success(function (data) {
                    vm.completeTema = null;
                    vm.listaTema = [];
                    rellenarListTema();
                    vm.verificar = false;
                }).error(function () {
                    console.log("fallo");
                    vm.completeTema = null;
                })
            }
        }

        /**
         * @author Eduardo Guerrero
         * Guarda los temas en una lista, esta lista es la que sera
         * mostrada en el autocomplete
         * @version 1.0
         */
        function rellenarListTema() {
            TemaCustomService.listarTemas().success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    vm.listaTema.push(data[i].nombre);
                }
            })
        }

        //-------------------------------------------------------------------

        function obtenerUsuarioNormal() {
            Principal.identity().then(function (account) {
                vm.account = account;
                PersonalizarCuenta.obtenerUsuarioNormal(account.id).success(function (response) {
                    vm.usuario = response;
                    obtenerSeguidores(vm.usuario.id);
                }).error(function (error) {
                    console.log("Problema obtenerUsuarioNormal");
                });
            });
        }

        function obtenerSeguidores() {
            ListarSeguidores.obtenerSeguidores(vm.usuario.id).success(function (response) {
                vm.seguidores = response;
            }).error(function (error) {
                console.log("Problema inesperado al traer seguidores de un usuario en controller de asignar colaboradores");
            });
        }

        vm.elegir = function (usuario) {
            vm.elegidos.push(usuario);
            var index = vm.seguidores.indexOf(usuario);
            vm.seguidores.splice(index, 1);
        }
        vm.pop = function (usuario) {
            vm.seguidores.push(usuario);
            var index = vm.elegidos.indexOf(usuario);
            vm.elegidos.splice(index, 1);
        }

        vm.asignarColaboradores = function () {
            vm.mostrarCol = true;
        }
        vm.volver = function () {
            vm.mostrarCol = false;

        }


        function crearCapitulos() {// se crean los capitulos ocn el id de la publicacion
            //SE DEBE CREAR UN USUARIO MAS YA Q EL USUARIO LOGUEADO ES UN COLABORADOR DE LA PUBLICACION
            var iteraciones = vm.publicacion.cantidadIteraciones * (vm.elegidos.length + 1);
            var capitulos = [];

            for (var i = 0; i < iteraciones; i++) {
                var capitulo = {
                    idPublicacionCId: vm.publicacion.id
                }
                capitulos.push(capitulo);
            }
            ConfigurarPublicacionCompartida.crearCapitulos({capitulo: capitulos}).success(function (response) {
                vm.capitulos = response;
                asignarElegidos();
            }).error(function (error) {
                console.log('error al crear capitulos compartida')
            });
        }

        function asignarElegidos() {
            //esta lista tiene los elegidos mas el usuario logueado q tambien es participante
            var colaboradoresList = [];
            vm.colaboraciones = []
            colaboradoresList.push(vm.usuario);
            for (var i = 0; i < vm.elegidos.length; i++) {
                colaboradoresList.push(vm.elegidos[i]);
            }
            console.log(colaboradoresList);

            var indiceColaborador = 0;

            for (var i = 0; i < vm.capitulos.length; i++) {
                console.log(indiceColaborador);

                if (indiceColaborador >= colaboradoresList.length) {
                    indiceColaborador = 0;
                }

                var colaborador = {
                    capituloCId: vm.capitulos[i].id,
                    publicacionId: vm.publicacion.id,
                    idUsuarioId: colaboradoresList[indiceColaborador].id
                }
                indiceColaborador++;
                vm.colaboraciones.push(colaborador);
            }

            ConfigurarPublicacionCompartida.registrarColaboradores({colaboradores: vm.colaboraciones}).success(function (response) {
                CompartidaServiceShare.enviarSiONo(response);
                $state.go('publicacionCompartidaEditor');
            }).error(function (error) {
                console.log('error al crear colaboradores')
            });

        }

    }
})();
