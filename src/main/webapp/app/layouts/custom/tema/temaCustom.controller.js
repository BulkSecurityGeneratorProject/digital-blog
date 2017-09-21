(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('TemaCustomController', TemaCustomController);

    TemaCustomController.$inject = ['$scope', '$state', '$stateParams', '$timeout', 'TemaCustomService'];

    function TemaCustomController($scope, $state, $stateParams, $timeout, TemaCustomService) {
        var vm = this;

        vm.temas = [];
        vm.listaTema = [];
        vm.agregar = agregarTemas;
        vm.completeTema = vm.completeTema;
        vm.tema = {};
        vm.verificar = false;
        listarTemas();
        rellenarListTema();
        vm.idTema = "";

        /**
         * @author Eduardo Guerrero
        *Actualiza constantemente la lista del autocompletar
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
        vm.obtenerIdTema = function(suggestion){
            TemaCustomService.listarTemas().success(function (data) {
                for (var i = 0; i < data.length; i++) {
                    if(suggestion == data[i].nombre){
                        vm.idTema = data[i].id;
                    }
                }
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
         * Lista los temas en la tabla meramente
         * para prueba
         * @version 1.0
         */
        function listarTemas() {
            TemaCustomService.listarTemas().success(function (data) {
                vm.temas = data;
            })
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
            if (verificarTema(resultado) == true) {
                vm.verificar = false;
            } else {
                TemaCustomService.agregarTema({type: vm.tema}).success(function (data) {
                    listarTemas();
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
