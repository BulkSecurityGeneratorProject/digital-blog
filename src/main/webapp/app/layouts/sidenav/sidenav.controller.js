(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SideNavController', SideNavController);

    SideNavController.$inject = ['$scope', '$stateParams', 'SeccionCustomService', 'Principal', '$state', 'SeccionServiceShare', 'toastr', '$uibModal'];

    function SideNavController($scope, $stateParams, SeccionCustomService, Principal, $state, SeccionServiceShare, toastr, $uibModal) {
        var vm = this;
        cargarSecciones();
        vm.listaSeccion = [];
        vm.idBiblioteca = null;
        vm.seccion = vm.idSeccion;
        vm.bibliotecaActual = null;
        vm.seccionID = $scope.seccionID;
        vm.animationsEnabled = true; //NO BORRAR
        /* Set the width of the side navigation to 250px and the left margin of the page content to 250px and add a black background color to body */
        vm.state = false;
        cargarSecciones();

        // openNav();
        // function openNav() {
        //     document.getElementById("mySidenav").style.width = "250px";
        //
        //     vm.state = true;
        //
        // }
        // vm.nav = function navButton() {
        //     if (vm.state == false) {
        //         vm.openNav();
        //     } else {
        //         vm.closeNav();
        //     }
        // }
        // vm.openNav = function openNav() {
        //     document.getElementById("mySidenav").style.width = "250px";
        //     document.getElementById("main").style.marginLeft = "250px";
        //     vm.state = true;
        //     cargarSecciones();
        // }

        /* Set the width of the side navigation to 0 and the left margin of the page content to 0, and the background color of body to white */
        // vm.closeNav = function closeNav() {
        //     document.getElementById("mySidenav").style.width = "0";
        //     document.getElementById("main").style.marginLeft = "0";
        //     vm.state = false;
        // }
        function cargarSecciones() {

            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if (account != null) {
                    //document.getElementById("main").style.marginLeft = "250px";
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

        /*
         *TODO:SULLIVAN, AQUI OBTIENE EL VALOR SELECCIONADO EN EL DROPDOWN
         */
        vm.obtenerIdSeleccionado = function (item) {

            $state.go("listarPublicacionesPorSeccion", {itemId: item.id, itemNombre: item.nombre});
        }


        /*
         *IMPLEMENTACION DEL MENU CONTEXT
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
                    } else {
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
                    } else {
                        return $itemScope.item.nombre.match("Otros") == null;
                    }
                }],
        ];
    }

})();


