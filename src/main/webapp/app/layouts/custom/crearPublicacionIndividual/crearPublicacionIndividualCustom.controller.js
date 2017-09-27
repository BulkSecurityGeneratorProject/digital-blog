/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        //Estandar: Nombre+CustomController
        .controller('CrearPublicacionIndividualCustomController', CrearPublicacionIndividualCustomController);

    CrearPublicacionIndividualCustomController.$inject = ['$rootScope', '$state', 'PublicacionServiceShare', 'DataUtils', '$scope', 'Principal', 'PersonalizarCuenta', 'Categoria','TemaCustomService'];

    function CrearPublicacionIndividualCustomController($rootScope, $state, PublicacionServiceShare, DataUtils, $scope, Principal, PersonalizarCuenta, Categoria,TemaCustomService) {
        var vm = this;

        vm.listaTema = [];
        vm.agregar = agregarTemas;
        vm.completeTema = vm.completeTema;
        vm.tema = {};
        vm.verificar = false;
        rellenarListTema();



        vm.crearPublicacion = crearPublicacion;
        vm.usuario = {};
        vm.publicacion = {
            estado: 1, //El estado es 1 que es borrado 2 publicada
        };

        //CUANDO VEA ESTE COMMENT Y TERMINA DE IMPLEMENTAR, ESTADO: FALTA IMPLEMENTAR (CAMBIE A ESTADO IMPLEMENTADO)
        vm.categorias = [];
        vm.temas = [{
            nombre: 'Salud',
            id: 1
        }, {
            nombre: 'Deporte',
            id: 2
        }]

        $scope.submitFrmPublicacion = function () {
            if ($scope.crearPublicacionFrm.$valid) {
                crearPublicacion();
            }
        };

        vm.init = function () {
            Principal.identity().then(function (account) {
                vm.cuentaActual = account;
                vm.publicacion.idUsuarioJhuser = vm.cuentaActual.id;
                vm.idUsuarioActual = vm.cuentaActual.id;
                obtenerUsuarioNormal();
            });

            Categoria.query(function (result) {
                vm.categorias = result;
                vm.searchQuery = null;
            });
        };

        vm.init();

        /**
         * @author Maureen Salas
         * Obtiene el usuario normal
         * @version 1.0
         */
        function obtenerUsuarioNormal() {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.idUsuarioActual).success(function (response) {
                vm.usuario = response;
                vm.publicacion.usuarioId = vm.usuario.id;
            }).error(function (error) {
            });
        }

        /**
         * @author José Nuñez
         * Crea una publicacion
         * @version 1.0
         */
        function crearPublicacion() {
            agregarTemas();
            PublicacionServiceShare.enviarPublicacion(vm.publicacion);
            $state.go('crearPublicacionIndividualEditor');
        }

        /**
         * @author José Nuñez
         * @param $file recibe una imagen
         * @version 1.0
         */
        vm.setFotopublicacion = function ($file) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        vm.publicacion.fotopublicacion = base64Data; //Lo vamos a utilizar
                    });
                });
            }
        };


        /**
         * @author José Nuñez
         * Actualiza constantemente la lista del autocompletar
         * @version 1.0
         */
        vm.updateTemas = function () {
            vm.temass = vm.listaTema;
        }


        /**
         * @author José Nuñez
         * Obtiene el id Seleccionado
         * @version 1.0
         */
        vm.obtenerIdTema = function(suggestion){
            TemaCustomService.listarTemas().success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    if(suggestion == data[i].nombre){
                        vm.publicacion.temaId = data[i].id;
                    }
                }
            })
        }

        /**
         * @author José Nuñez
         * Verifica que la palabra ingresada no este repetido
         * @param tema lo recibe desde el completar
         * @return  boolean true si esta ya en la lista, false si no esta
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
         * Agrega el tema que no esta registrado
         * @version 1.0
         */
        function agregarTemas() {
            //Transforma el texto con la primera letra en mayuscula
            //y el resto en miniscula.
            vm.tema = {nombre: vm.completeTema};
            var texto = vm.tema.nombre;
            var primero = texto.charAt(0);
            var resultado = primero.toUpperCase() + texto.substring(1, texto.length).toLowerCase()
            if (verificarTema(resultado) == true) {
                vm.verificar = false;
            } else {
                TemaCustomService.agregarTema({type: vm.tema}).success(function (data) {
                    vm.completeTema = null;
                    vm.listaTema = [];
                    rellenarListTema();
                    vm.verificar = false;
                }).error(function () {
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

}

})();
