(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('AgregarSeccionCustomDialogController', AgregarSeccionCustomDialogController);

    AgregarSeccionCustomDialogController.$inject = ['$scope', 'Principal', 'toastr', '$uibModalInstance', 'SeccionCustomService', 'AgregarSeccionCustomService', 'PublicacionServiceShare', 'NotificacionGeneral'];

    function AgregarSeccionCustomDialogController($scope, Principal, toastr, $uibModalInstance, SeccionCustomService, AgregarSeccionCustomService, PublicacionServiceShare, NotificacionGeneral) {
        var vm = this;
        cargarSecciones();
        vm.save = save;
        vm.publicacion = PublicacionServiceShare.publicacion;
        vm.errorConnection = false;
        vm.errorDuplicado = false;
        vm.listaSeccion = [];

        //Cierra el modal
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        /**
         * @author Eduardo Guerrero
         * Agrega una publicacion publicada a una seccion en la biblioteca personal del USUARIO LOGEADEO
         * @version 1.0
         */
        function save() {
            vm.isSaving = true;
            vm.seccionPublicacion = {
                idSeccionSPId: vm.seccion.id,
                idPublicacionSPId: vm.publicacion.id
            }
            AgregarSeccionCustomService.agregarPublicacionAseccion({seccionPublicacion: vm.seccionPublicacion}, onSaveError()).success(function (data) {
                $uibModalInstance.close(data);
                NotificacionGeneral.notificacion('', 'Se agrego ' + vm.publicacion.titulo + ' a su biblioteca', 1);

            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }else if(response.status === 400){
                    vm.errorDuplicado = true;
                }

            })
            vm.errorConnection = false;
            vm.errorDuplicado = false;
        }

        /**
         * @author Eduardo Guerrero
         * Carga las secciones de la biblioteca del usuario logeado
         * para eligir a cual seccion quiere agregar su publicacion
         * @version 1.0
         */
        function cargarSecciones() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if (account != null) {
                    SeccionCustomService.obtenerBibliotecaPorJhiUser(vm.account.id).success(function (data) {
                        vm.idBiblioteca = data.id;
                        vm.bibliotecaActual = vm.idBiblioteca;
                        /*
                         Aqui empieza la funcion para listar las secciones
                         */
                        SeccionCustomService.listarSeccion(vm.idBiblioteca).success(function (data) {
                            /*
                            *El ciclo verifica si el nombre de la seccion es igual a "Mis publiciones"
                            * Esto no permite que el usuario puede guardar publicaciones dentro de esa seccion.
                             */
                            for(var i = 0; i < data.length; i++){
                                if(data[i].nombre == "Mis publicaciones" || data[i].nombre == "Reportes"){

                                }else{
                                    vm.secciones = {
                                        id:data[i].id,
                                        nombre:data[i].nombre,
                                        bibliotecaId:data[i].bibliotecaId
                                    };
                                    vm.listaSeccion.push(vm.secciones);
                                }
                            }

                        }).error(function () {

                        })
                    }).catch(function (response) {
                        if (response.status === 500) {
                            vm.errorConnection = true;
                        }

                    });
                }

            });
            vm.errorConnection = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
