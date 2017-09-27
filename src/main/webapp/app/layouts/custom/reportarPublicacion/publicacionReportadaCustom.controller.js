(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaCustomController', PublicacionReportadaCustomController);

    PublicacionReportadaCustomController.$inject = ['$state','PublicacionReportada', 'Publicacion', 'PublicacionServiceShare', '$uibModal', 'BibliotecaGeneralService', 'LikeT', 'NotificacionGeneral', 'PublicacionReportadaCustom', 'SeccionCustomService', 'AgregarSeccionCustomService','$window'];

    function PublicacionReportadaCustomController($state,PublicacionReportada, Publicacion, PublicacionServiceShare, $uibModal, BibliotecaGeneralService, LikeT, NotificacionGeneral, PublicacionReportadaCustom, SeccionCustomService, AgregarSeccionCustomService,$window) {

        var vm = this;

        vm.publicacionReportadas = [];
        vm.publicacionesRP = [];
        vm.errorConnection = false;
        loadAll();
        cargarLikes();

        /**
         * @author Eduardo Guerrero
         * carga las publicaciones reportadas
         * @version 1.0
         */
        function loadAll() {
            PublicacionReportada.query(function (result) {
                vm.publicacionReportadas = result;
                vm.searchQuery = null;
                loadAllPublicaciones();
            });
        }

        /**
         * @author Eduardo Guerrero
         *carga los likes del usuario
         * @version 1.0
         */
        function cargarLikes() {
            LikeT.query(function (result) {
                vm.likeTS = result;
                vm.searchQuery = null;
            });
        }

        /**
         * @author Eduardo Guerrero
         * carga todas las publicaciones existentes
         * @version 1.0
         */
        function loadAllPublicaciones() {
            Publicacion.query(function (result) {
                vm.publicaciones = result;
                vm.searchQuery = null;
                cargarPublicacionPorIdPublicacionReportada();
            });
        }

        /**
         * @author Eduardo Guerrero
         * Matchea el id de la publicacion con el id de la publicacion reportada
         * Se crea le objeto publicacion con el atributo cantidadReportes
         * Se crea una nueva lista y se agrega esos nuevos objetos a la lista
         * @version 1.0
         */
        function cargarPublicacionPorIdPublicacionReportada() {
            for (var i = 0; i < vm.publicacionReportadas.length; i++) {
                for (var x = 0; x < vm.publicaciones.length; x++) {
                    if (vm.publicacionReportadas[i].idPublicacion == vm.publicaciones[x].id) {
                        //Crea el objeto publicacion y se le agrega el atributo cantidad de reportes
                        //Para mostrarlo en la vista.
                        var publicacion = {
                            cantidadIteraciones: vm.publicaciones[x].cantidadIteraciones,
                            categoriaId: vm.publicaciones[x].categoriaId,
                            contenido: vm.publicaciones[x].contenido,
                            descripcion: vm.publicaciones[x].descripcion,
                            estado: vm.publicaciones[x].estado,
                            fotoPublicador: vm.publicaciones[x].fotoPublicador,
                            fotopublicacion: vm.publicaciones[x].fotopublicacion,
                            fotopublicacionContentType: vm.publicaciones[x].fotopublicacionContentType,
                            id: vm.publicaciones[x].id,
                            temaId: vm.publicaciones[x].temaId,
                            tipo: vm.publicaciones[x].tipo,
                            titulo: vm.publicaciones[x].titulo,
                            urlImagen: vm.publicaciones[x].urlImagen,
                            usuarioId: vm.publicaciones[x].usuarioId,
                            cantidadReportes: vm.publicacionReportadas[i].cantidadReportes
                        };
                        vm.publicacionesRP.push(publicacion);
                    }
                }
            }
        }

        /**
         * @author Jose Nuñez
         * Envía la publicación seleccionada al visor para poder verla.
         * Utiliza un broadcast para envíar la publicación
         * @version 1.0
         */
        vm.verPublicacion = function (publicacion) {
            var url = $state.href('verPublicacion', {id: publicacion.id, tipo: 2});
            $window.open(url, '_blank');
        }


        /**
         * @author Eduardo Guerrero
         * Modifica la publicacion original la cual fue reportada
         * Pone el contenido en 0 y el estado en 1 porque ya no esta publicada
         * Se quita la publicacion de la listaNueva que se creo para que no sea visible en el view
         * Si se reporta se elimina la publicacion de la tabla publicacion_reportada
         * Se elimina los likes de la publicacion original dejandola en 0
         * @param publicacion
         * @version 1.0
         */
        vm.reportar = function (publicacion) {
            var publicacionRp = publicacion;
            publicacionRp.estado = 1;
            publicacionRp.contenido = "0";
            BibliotecaGeneralService.updatePublicacion(publicacionRp).success(function () {
            vm.publicacionesRP.splice(publicacion, 1);
            for (var i = 0; i < vm.publicacionReportadas.length; i++) {
                if (vm.publicacionReportadas[i].idPublicacion == publicacion.id) {
                    var publicacionReportar = {
                        id: vm.publicacionReportadas[i].id,
                        cantidadReportes: vm.publicacionReportadas[i].cantidadReportes,
                        idPublicacion: vm.publicacionReportadas[i].idPublicacion
                    }
                    PublicacionReportada.delete(publicacionReportar);
                }
                for (var x = 0; x < vm.likeTS.length; x++) {
                    if (vm.likeTS[x].idLikePId == publicacion.id) {
                        var likeT = {
                            id: vm.likeTS[x].id,
                            idUsuarioLId: vm.likeTS[x].idUsuarioLId,
                            idLikePId: vm.likeTS[x].idLikePId
                        };
                        LikeT.delete(likeT);
                    }
                }
            }
            guardarPublicacionEnSeccionReportes(publicacion);
            NotificacionGeneral.notificacion('', 'Se reporto ' + publicacion.titulo, 1);
            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });

        vm.errorConnection = false;
        }

        /**
         * @author Eduardo Guerrero
         * Guarda la publicacion en la seccion de reportes en la biblioteca al que pertenece la publicacion reportada
         * @param publicacion
         * @version 1.0
         */
        function guardarPublicacionEnSeccionReportes(publicacion) {
           console.log(publicacion.usuarioId);
            PublicacionReportadaCustom.conseguirJhiUserPorIdUsuario({id:publicacion.usuarioId}).success(function (data) {
                vm.jhiUser = data;
                if (vm.jhiUser != null) {
                    SeccionCustomService.obtenerBibliotecaPorJhiUser(vm.jhiUser.idJHIUser).success(function (data) {
                        vm.idBiblioteca = data.id;
                        vm.bibliotecaActual = vm.idBiblioteca;
                        /*
                         Aqui empieza la funcion para listar las secciones
                         */
                        SeccionCustomService.listarSeccion(vm.idBiblioteca).success(function (data) {
                            vm.listarSecciones = data;
                            for (var x = 0; x < vm.listarSecciones.length; x++) {
                                if (vm.listarSecciones[x].nombre == "Reportes") {
                                    vm.idSeccionReporte = vm.listarSecciones[x].id;
                                    break;
                                }
                            }
                            vm.seccionPublicacion = {
                                idSeccionSPId: vm.idSeccionReporte,
                                idPublicacionSPId: publicacion.id
                            }
                            AgregarSeccionCustomService.agregarPublicacionAseccion({seccionPublicacion: vm.seccionPublicacion});
                        })
                    })
                }

            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });
            vm.errorConnection = false;
        }


        /**
         * @author Eduardo Guerrero
         * Cancelar reporte cancela el reporte en caso de que el moderador no vea nada sospechoso en la publicacion
         * Elimina la publicacion reportada de la tabla publicacion_reportada.
         * @param publicacion
         * @version 1.0
         */
        vm.cancelarReporte = function (publicacion) {
            for (var i = 0; i < vm.publicacionReportadas.length; i++) {
                if (vm.publicacionReportadas[i].idPublicacion == publicacion.id) {
                    var pr = {
                        id: vm.publicacionReportadas[i].id,
                        cantidadReportes: vm.publicacionReportadas[i].cantidadReportes,
                        idPublicacion: vm.publicacionReportadas[i].idPublicacion
                    }
                    vm.publicacionesRP.splice(publicacion, 1);
                    PublicacionReportada.delete(pr);
                }
            }
            NotificacionGeneral.notificacion('', 'Se cancelo el reporte de ' + publicacion.titulo, 1);
        }


    }
})();
