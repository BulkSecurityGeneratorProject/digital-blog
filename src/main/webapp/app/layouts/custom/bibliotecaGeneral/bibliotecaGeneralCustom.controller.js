(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('BibliotecaGeneralCustomController', BibliotecaGeneralCustomController);

    BibliotecaGeneralCustomController.$inject = ['Principal', '$scope', 'PersonalizarCuenta', 'BibliotecaGeneralService', '$state', 'LikeT', 'Tema', 'Categoria', 'PublicacionServiceShare', '$uibModal', 'BibliotecaGeneralServiceShare','Publicacion','$window'];

    function BibliotecaGeneralCustomController(Principal, $scope, PersonalizarCuenta, BibliotecaGeneralService, $state, LikeT, Tema, Categoria, PublicacionServiceShare, $uibModal, BibliotecaGeneralServiceShare,Publicacion,$window) {
        var vm = this;
        vm.cargar = cargarPublicacionPorBusqueda;
        vm.cargarCatYTema = cargarPublicacionPorCategoriaYtema;
        cargarAllLikeT();
        cargarCategoria();
        cargarTemas();
        vm.cargarTodasLasPublicaciones = cargarPublicacionPublicada;
        vm.nombreCategoria = "";
        vm.nombreTema = "";
        vm.idPrueba = 0;
        vm.estadoLike = false;
        vm.publiLikes = [];
        vm.publicacionLikes = [];
        vm.noLikes = [];
        vm.seEncontro = false;

        /**
         *@author Eduardo Guerrero
         *Obtiene el nombre de la categoria seleccionada
         *@version 1.0
         */
        Principal.identity().then(function (account) {
            vm.cuentaActual = account;
        });

        $scope.$watch("vm.buscar", function (now, old) {
            if (now) {
                vm.error = "";
            } else {
                vm.error = "Escribe una consulta en el cuadro.";
                cargarPublicacionPublicada();
            }
        });


        /**
         *@author Eduardo Guerrero
         *Comprueba que el id del usuario sea igual para el id de la publicacion para que el mismo usuario no pueda reportar su publicacion
         *@param publicacion para obtener boton de la publicacion reportar
         *@version 1.0
         */
        vm.comprobar = function (publicacion) {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function (response) {
                vm.usuario = response;
                if (vm.usuario.id == publicacion.usuarioId) {
                    vm.seEncontro = true;
                } else {
                    vm.seEncontro = false;

                }
            })

        }


        /**
         *@author Eduardo Guerrero
         *Obtiene el nombre del categoria seleccionado
         *@version 1.0
         */
        vm.obtenerValorCategoria = function () {
            var categoriaId = vm.categoria;
            console.log(categoriaId);
            vm.nombreCategoria = $.grep(vm.categorias, function (categoria) {
                return categoria.id == categoriaId;
            })[0].nombre;
        }

        /**
         *@author Eduardo Guerrero
         *Obtiene el nombre del tema seleccionado
         *@version 1.0
         */
        vm.obtenerValorTema = function () {
            var temaId = vm.tema;
            vm.nombreTema = $.grep(vm.temas, function (tema) {
                return tema.id == temaId;
            })[0].nombre;
        }


        /**
         * @author Eduardo Guerrero
         * Observa siempre el input para ver si se encuentra vacio
         * si se escribe algo, y pone en null, carga las publicaciones de nuevo.
         * Busca por parametro la publicacion que se le esta enviando
         * ya se por autor,nombre,tema,categoria
         * @version 1.0
         */

        function cargarPublicacionPorBusqueda() {
            if (vm.buscar == null) {
                vm.error = "Escribe una consulta en el cuadro.";
            } else {
                vm.error = "";
                BibliotecaGeneralService.cargarPublicacionesPorBusqueda({nombre: vm.buscar}).success(function (data) {
                    vm.publicacionLikes = data;
                    cargarListaLikes();
                });
            }
        }


        /**
         *@author Eduardo Guerrero
         *Carga las publicaciones por categoria y tema
         *@version 1.0
         */
        function cargarPublicacionPorCategoriaYtema() {
            if (vm.categoria == null || vm.categoria == "Seleccione una categoria" || vm.tema == null || vm.tema == "Seleccione un tema") {
            } else {
                BibliotecaGeneralService.cargarPublicacionPorCategoriaYTema({
                    nombre: vm.nombreCategoria,
                    temaNombre: vm.nombreTema
                }, onSaveError).success(function (data) {
                    vm.publicacionLikes = data;
                    cargarListaLikes();
                    $("#dlDropDown").dropdown("toggle");
                }).catch(function (response) {
                    if (response.status === 500) {
                        vm.errorConnection = true;
                    }
                });
            }
            vm.errorConnection = false;

        }


        /**
         *@author Eduardo Guerrero
         *Carga todas las publicaciones que fueron publicadas
         *@version 1.0
         */
        function cargarPublicacionPublicada() {
            /**
             *Verifica si el contenido esta null, si lo esta se le setea el valor a 0
             */
            BibliotecaGeneralService.cargarPublicacionPublicada().success(function (data) {
                cargarCategoria();
                cargarTemas();
                vm.publicaciones = data;
                for (var i = 0; i < vm.publicaciones.length; i++) {
                    if (vm.publicaciones[i].contenido == null) {
                        vm.publicaciones[i].contenido = "0";
                        BibliotecaGeneralService.updatePublicacion(vm.publicaciones[i]);
                    }
                }
                vm.publicacionLikes = data;
                cargarListaLikes();
            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });
            vm.errorConnection = false;
        }


        /**
         *@author Eduardo Guerrero
         *Carga la lista de los likes
         *Se agrega un atributo del objeto
         *@version 1.0
         */
        function cargarListaLikes() {
            vm.listaPublicacionesLikes = [];
            for (var i = 0; i < vm.publicacionLikes.length; i++) {
                var publicacion = {
                    cantidadIteraciones: vm.publicacionLikes[i].cantidadIteraciones,
                    categoriaId: vm.publicacionLikes[i].categoriaId,
                    contenido: vm.publicacionLikes[i].contenido,
                    descripcion: vm.publicacionLikes[i].descripcion,
                    estado: vm.publicacionLikes[i].estado,
                    fotoPublicador: vm.publicacionLikes[i].fotoPublicador,
                    fotopublicacion: vm.publicacionLikes[i].fotopublicacion,
                    fotopublicacionContentType: vm.publicacionLikes[i].fotopublicacionContentType,
                    id: vm.publicacionLikes[i].id,
                    temaId: vm.publicacionLikes[i].temaId,
                    tipo: vm.publicacionLikes[i].tipo,
                    titulo: vm.publicacionLikes[i].titulo,
                    urlImagen: vm.publicacionLikes[i].urlImagen,
                    usuarioId: vm.publicacionLikes[i].usuarioId,
                    estadoLike: false
                };
                vm.listaPublicacionesLikes.push(publicacion);
            }
            verificarLikeSeguimiento();
        }


        /**
         * @author Eduardo Guerrero
         * Verifica si el usuario esta logueado
         * @see  cargarListaLikes
         * @version 1.0
         */
        function verificarLikeSeguimiento() {
            Principal.identity().then(function (account) {
                vm.cuentaActual = account;
                vm.LikesPublicacion = [];
                PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function (response) {
                    vm.UsuarioLogueado = response;
                    recorrerListaPublicacionesLikes();
                })
            })
        }

        /**
         * @author Eduardo Guerrero
         * Obtiene el indice de la cantidad de likes por publicacion del usuario
         * @see  verificarLikeSeguimiento
         * @version 1.0
         */
        function recorrerListaPublicacionesLikes() {
            for (var i = 0; i < vm.listaPublicacionesLikes.length; i++) {
                verificarPublicacionLikeTS(i);
            }
        }

        /**
         * @author Eduardo Guerrero
         * Verifica el like por publicacion
         * @see  recorrerListaPublicacionesLikes
         * @version 1.0
         */
        function verificarPublicacionLikeTS(indice) {
            for (var i = 0; i < vm.likeTS.length; i++) {
                if (vm.likeTS[i].idLikePId == vm.listaPublicacionesLikes[indice].id && vm.UsuarioLogueado.id == vm.likeTS[i].idUsuarioLId) {
                    vm.listaPublicacionesLikes[indice].estadoLike = true;
                }
            }
        }

        /**
         * @author Eduardo Guerrero
         * Carga todos los likes del usuario
         * @version 1.0
         */
        function cargarAllLikeT() {
            LikeT.query(function (result) {
                vm.likeTS = result;
                vm.searchQuery = null;
            });
        }

        /**
         * @author Eduardo Guerrero
         * Carga todos los temas
         * @version 1.0
         */
        function cargarTemas() {
            Tema.query(function (result) {
                vm.temas = result;
                vm.searchQuery = null;
            });
        }

        /**
         * @author Eduardo Guerrero
         * Agrega una publicacion a una seccion en la biblioteca personal.
         * @param publicacion la cual se desea agregar a la seccion
         * @version 1.0
         */
        vm.agregarSeccion = function (publicacion) {
            PublicacionServiceShare.enviarPublicacion(publicacion);
            var modalInstance = $uibModal.open({
                animation: vm.animationsEnabled,
                templateUrl: 'app/layouts/custom/agregarSeccion/agregarSeccionCustom.html',
                controller: 'AgregarSeccionCustomDialogController',
                controllerAs: 'vm',
                size: 'md'
            });
        }


        /**
         * @author Eduardo Guerrero
         * Carga las categorias
         * @version 1.0
         */
        function cargarCategoria() {
            Categoria.query(function (result) {
                vm.categorias = result;
                vm.searchQuery = null;
            });
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        /**
         * @author Eduardo Guerrero
         * Agrega el like a la publicacion
         * @param PublicacionObjeto recibe la publicacion como parametro
         * @param $index posicion de la publicacion a la cual le debe cambiar el icon.
         * @version 1.0
         */
        vm.like = function (PublicacionObjeto, $index) {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function (data) {
                vm.usuarioNormal = data;
                vm.likeT = {
                    idUsuarioLId: vm.usuarioNormal.id,
                    idLikePId: PublicacionObjeto.id
                };
                //Busca el like del usuario por publicacion
                BibliotecaGeneralService.obtenerLikeDeUsuario({
                    idUsuario: vm.likeT.idUsuarioLId,
                    idPublicacion: vm.likeT.idLikePId
                }).success(function (data) {
                    //Si encuentra el like del usuario en la publicacion
                    // Quita el like a la publicacion.
                    vm.quitarLike(PublicacionObjeto, $index);
                }).error(function () {
                    //Si no lo encuentra va y inserta el like del usuario y la publicacion a la BD
                    BibliotecaGeneralService.likePublicacion({like: vm.likeT}).success(function () {
                        //Parsea el contenido de la publicacion
                        var contador = parseInt(PublicacionObjeto.contenido);
                        contador = contador + 1;
                        PublicacionObjeto.contenido = contador.toString();
                        //aumenta el contador en 1 y le hace update a la publicacion
                        BibliotecaGeneralService.updatePublicacion(PublicacionObjeto);
                        vm.listaPublicacionesLikes[$index].estadoLike = true;
                        //Cambia el estado del icon
                        cargarAllLikeT();
                    })
                })

            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });
            vm.errorConnection = false;
        }

        /**
         * @author Eduardo Guerrero
         * Quita el like a la publicacion
         * @param PublicacionObjeto recibe la publicacion como parametro
         * @param $index posicion de la publicacion a la cual le debe cambiar el icon.
         * @version 1.0
         * @see vm.like()
         */
        vm.quitarLike = function (PublicacionObjeto, $index) {
            //Busca el like del usuario
            BibliotecaGeneralService.obtenerLikeDeUsuario({
                idUsuario: vm.likeT.idUsuarioLId,
                idPublicacion: vm.likeT.idLikePId
            }).success(function (data) {
                vm.likeUsuario = data;
                //parse el contenido de la publicacion ya que es string y se ocupa int
                LikeT.delete({id: vm.likeUsuario.id});
                var contador = parseInt(PublicacionObjeto.contenido);
                contador = contador - 1;
                PublicacionObjeto.contenido = contador.toString();
                //Cambia el estado del icon
                vm.listaPublicacionesLikes[$index].estadoLike = false;
                //Hace el update a la publicacion cuando el contador cambia
                BibliotecaGeneralService.updatePublicacion(PublicacionObjeto);
                cargarAllLikeT();
            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });
            vm.errorConnection = false;
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
         * @author Christopher Sullivan
         * dirige a la pagina donde se comenta la publicacion
         * @param id
         * @version 1.0
         */
        vm.comentar = function (id) {
            $state.go("comentarioSobrePublicacion", ({idPublicacion: id}));
        }

        /**
         * @author Eduardo Guerrero
         * Reportar publicacion
         * @param recibe la publicacion
         * @version 1.0
         */
        vm.reportarPublicacion = function (pb) {
            BibliotecaGeneralServiceShare.enviarPublicacion(pb);
            var modalInstance = $uibModal.open({
                animation: vm.animationsEnabled,
                templateUrl: 'app/layouts/custom/reportarPublicacion/publicacionReportada.html',
                controller: 'PublicacionReportadaDialogCustomController',
                controllerAs: 'vm',
                size: 'md'
            })
        }

        /**
         * @author Eduardo Guerrero
         * Reportar publicacion
         * @version 1.0
         */
        vm.reportarPublicacion = function (pb) {
            BibliotecaGeneralServiceShare.enviarPublicacion(pb);
            var modalInstance = $uibModal.open({
                animation: vm.animationsEnabled,
                templateUrl: 'app/layouts/custom/reportarPublicacion/publicacionReportada.html',
                controller: 'PublicacionReportadaDialogCustomController',
                controllerAs: 'vm',
                size: 'md'

            });

        }
    }

})
();
