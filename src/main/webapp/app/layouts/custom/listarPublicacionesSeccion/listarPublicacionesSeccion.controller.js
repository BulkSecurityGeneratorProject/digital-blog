(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('listarPublicacionesPorSeccionController', listarPublicacionesPorSeccionController);

    listarPublicacionesPorSeccionController.$inject = ['$uibModal','$stateParams','$scope', 'Principal', 'LoginService', '$state','listarPublicacionesPorSeccionService','PersonalizarCuenta','SeccionCustomService','SeccionServiceShare','PublicacionServiceShare','SeccionPublicacion','$window'];

    function listarPublicacionesPorSeccionController ($uibModal,$stateParams,$scope, Principal, LoginService, $state,listarPublicacionesPorSeccionService,PersonalizarCuenta,SeccionCustomService,SeccionServiceShare,PublicacionServiceShare,SeccionPublicacion,$window) {
        var vm = this;
        vm.usuarioNormalLogged;
        vm.publicaciones;
        vm.temaNombre;
        vm.seccionId=$stateParams.itemId;
        vm.seccionNombre=$stateParams.itemNombre;
        vm.listaSeccion = [];
        vm.idBiblioteca = null;
        vm.seccion = vm.idSeccion;
        vm.bibliotecaActual = null;
        vm.seccionID = $scope.seccionID;
        vm.animationsEnabled = true; //NO BORRAR
        /* Set the width of the side navigation to 250px and the left margin of the page content to 250px and add a black background color to body */
        vm.state = false;
        cargarSecciones();
        vm.usuario ;

        function obtenerUsuarioNormal(){
            PersonalizarCuenta.obtenerUsuarioNormal(vm.account.id).success(function(response){
                vm.usuario=response;
            }).error(function (error){
            });
        }

        /**
         * @author Eduardo Guerrero
         * Obtiene la secciones por la biblioteca del usuario logueado
         * @version 1.0
         */
        function cargarSecciones() {

            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if (account != null) {
                    obtenerUsuarioNormal();
                   document.getElementById("listPubliSecc").style.marginLeft = "250px";
                    SeccionCustomService.obtenerBibliotecaPorJhiUser(vm.account.id).success(function (data) {
                        vm.idBiblioteca = data.id;
                        vm.bibliotecaActual = vm.idBiblioteca;
                        /*
                         Aqui empieza la funcion para listar las secciones
                         */
                        SeccionCustomService.listarSeccion(vm.idBiblioteca).success(function (data) {
                            vm.listaSeccion = data;
                        }).error(function () {
                        });
                    });
                }

            });
        }

        /**
         * @author Christopher Sullivan
         * Obtiene la secciones por la biblioteca del usuario logueado
         * @version 1.0
         */

        vm.obtenerIdSeleccionado = function (item) {
            vm.selectedSeccion = item.id;
            if(item.nombre === 'Mis publicaciones'){
                vm.mostrarEditar = true;
                console.log(item.nombre);
            }else{
                vm.mostrarEditar = false;
            }
            //$state.go("listarPublicacionesPorSeccion", {itemId: item.id, itemNombre: item.nombre});
            getPublicacionTema(item.id,item.nombre);
        }



        /**
         * @author Eduardo Guerrero
         * IMPLEMENTACION DEL MENU CONTEXT
         * @type {[*]}
         * @version 1.0
         * Inicio del menu context
         */
        //Menu de seccion para agregarlo
        vm.seccionMenu = [
            /*
             * Se paso el modal para este controlador
             */
            ['Agregar seccion', function () {
                SeccionServiceShare.enviarIdBiblioteca(vm.bibliotecaActual);
                var modalInstance = $uibModal.open({
                    animation: vm.animationsEnabled,
                    templateUrl: 'app/layouts/custom/seccion/seccionCustomDialog.html',
                    controller: 'SeccionCustomDialogController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null,
                                bibliotecaId: null
                            };
                        }
                    }
                });
                /*
                 *Vuelve a cargar la informacion
                 */
                modalInstance.result.then(function () {
                    cargarSecciones();
                });

            }],
        ];

        //Menu para eliminar y editar la seccion
        vm.menuOptions = [
            ['Eliminar seccion', function ($itemScope) {
                var modalInstance = $uibModal.open({
                    animation: vm.animationsEnabled,
                    templateUrl: 'app/layouts/custom/seccion/seccionCustomDeleteDialog.html',
                    controller: 'SeccionCustomDeleteController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['Seccion', function (Seccion) {
                            return Seccion.get({id: $itemScope.item.id}).$promise;
                        }]
                    }
                });
                modalInstance.result.then(function () {
                    cargarSecciones();
                });

            }, /*
             *Verifica que si las palabras hacen match con el nombre
             * las devuelve null y se desactiva la opcion en estas.
             */
                function ($itemScope) {
                    if ($itemScope.item.nombre == "Por leer") {
                        return $itemScope.item.nombre.match("Por leer") == null;
                    } else if ($itemScope.item.nombre == "Favoritos") {
                        return $itemScope.item.nombre.match("Favoritos") == null;
                    } else if ($itemScope.item.nombre == "Mis publicaciones") {
                        return $itemScope.item.nombre.match("Mis publicaciones") == null;
                    } else if ($itemScope.item.nombre == "Borrador") {
                        return $itemScope.item.nombre.match("Borrador") == null;
                    } else if ($itemScope.item.nombre == "Reportes") {
                        return $itemScope.item.nombre.match("Reportes") == null;
                    }
                    else {
                        return $itemScope.item.nombre.match("Otros") == null;
                    }
                }],
            null,
            ['Editar seccion', function ($itemScope) {
                SeccionServiceShare.enviarIdBiblioteca(vm.bibliotecaActual);
                var modalInstance = $uibModal.open({
                    animation: vm.animationsEnabled,
                    templateUrl: 'app/layouts/custom/seccion/seccionCustomDialogEdit.html',
                    controller: 'SeccionCustomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seccion', function (Seccion) {
                            return Seccion.get({id: $itemScope.item.id}).$promise;
                        }]
                    }
                });
                modalInstance.result.then(function () {
                    cargarSecciones();
                });

            }, /*
             *Verifica que si las palabras hacen match con el nombre
             * las devuelve null y se desactiva la opcion en estas.
             */
                function ($itemScope) {
                    if ($itemScope.item.nombre == "Por leer") {
                        return $itemScope.item.nombre.match("Por leer") == null;
                    } else if ($itemScope.item.nombre == "Favoritos") {
                        return $itemScope.item.nombre.match("Favoritos") == null;
                    } else if ($itemScope.item.nombre == "Mis publicaciones") {
                        return $itemScope.item.nombre.match("Mis publicaciones") == null;
                    } else if ($itemScope.item.nombre == "Borrador") {
                        return $itemScope.item.nombre.match("Borrador") == null;
                    } else if ($itemScope.item.nombre == "Reportes") {
                        return $itemScope.item.nombre.match("Reportes") == null;
                    }
                    else {
                        return $itemScope.item.nombre.match("Otros") == null;
                    }
                }],
        ];

        /**
         * Fin del menu Context
         */

        //TODO: REVISAR SI SE PUEDE ELIMINAR ESTE CODE DOCUMENTADO
        //     ={
        //     titulo: "lo que sea",
        //     descripcion: "desc lo que sea",
        //     urlImagen: "../../../../content/images/background2.jpg"
        // }
        // vm.publicaciones2=[
        //     vm.publicaciones
        //     // "http://res.cloudinary.com/dblelmjha/image/upload/v1489985921/u0nfdxupefkolh5ytd2k.png",
        //     // "../../../../content/images/iconProfile.png",
        //     // "../../../../content/images/background2.jpg",
        //     // "http://res.cloudinary.com/dblelmjha/image/upload/v1489985921/u0nfdxupefkolh5ytd2k.png",
        //     // "../../../../content/images/iconSearch.png",
        //     // "http://res.cloudinary.com/dblelmjha/image/upload/v1489985921/u0nfdxupefkolh5ytd2k.png",
        //     // "../../../../content/images/background.jpg",
        //     // "http://res.cloudinary.com/dblelmjha/image/upload/v1489985921/u0nfdxupefkolh5ytd2k.png",
        //     // "../../../../content/images/iconLike.png",
        //     // "../../../../content/images/creativity.svg",
        //     // "http://res.cloudinary.com/dblelmjha/image/upload/v1489985921/u0nfdxupefkolh5ytd2k.png"
        //     ]


        Principal.identity().then(function(account) {
            vm.cuentaActual = account;
            PersonalizarCuenta.obtenerUsuarioNormal(vm.cuentaActual.id).success(function(response){
                vm.usuarioNormalLogged=response;
                // getPublicacionTema(vm.usuarioNormalLogged.id)
            });
        });



         // getPublicacionTema();
        /**
         * @author Christopher Sullivan
         * se trae todas las publicaciones en la secccion
         * @param seccionId
         * @param seccionNombre
         * @version 1.0
         */
        function getPublicacionTema(seccionId,seccionNombre) {

            listarPublicacionesPorSeccionService.getPublicacionTema({seccion:seccionId}).success(function (response) {
                vm.publicaciones=response;
                vm.temaNombre=seccionNombre;
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
         * dirige a la pagina donde se somenta la publicacion
         * @param id
         * @version 1.0
         */
        vm.comentar=function (id) {
        $state.go("comentarioSobrePublicacion",({idPublicacion:id}));
        }


        /**
         * @author Maureen Leon
         * Elimina la publicacion de una seccion en particular
         * @param id
         * @version 1.0
         */
        vm.eliminar= function(publicacion){
            var seccionPublic = {
                idPublicacionSPId:publicacion.id,
                idSeccionSPId:vm.selectedSeccion
            }
            listarPublicacionesPorSeccionService.eliminar({obj:seccionPublic}).success(function (response) {
                SeccionPublicacion.delete({id: response.id});
                var index = vm.publicaciones.indexOf(publicacion);
                vm.publicaciones.splice(index, 1);
            }).error(function () {
            });


        }

        /**
         * @author Jose Nuñez
         * Modifica la publicacion de una seccion en particular
         * @param id
         * @version 1.0
         */
        vm.modificarPublicacion = function(publicacion) {
            PublicacionServiceShare.enviarPublicacion(publicacion,null);
            $state.go('modificarPublicacionIndividual');
        }
    }
})();
