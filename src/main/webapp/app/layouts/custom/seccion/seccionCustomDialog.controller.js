(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionCustomDialogController', SeccionCustomDialogController);

    SeccionCustomDialogController.$inject = ['PersonalizarCuenta', '$scope', 'Principal', '$uibModalInstance', 'entity', 'Seccion', 'Biblioteca','SeccionServiceShare','SeccionCustomService','NotificacionGeneral'];

    function SeccionCustomDialogController (PersonalizarCuenta, $scope, Principal, $uibModalInstance, entity, Seccion, Biblioteca,SeccionServiceShare,SeccionCustomService,NotificacionGeneral) {
        var vm = this;

        vm.seccion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bibliotecas = Biblioteca.query();
        vm.idBiblioteca  = SeccionServiceShare.seccion;
        vm.update = update;
        vm.errorConnection = false;
        vm.errorDuplicado = false;
        vm.comprobarNombre = false;
        cargarSecciones();

        /**
         * @author Eduardo Guerrero
         * Escuha la el id de la biblioteca que se le esta enviando para saber a cual
         * biblioteca se debe guardar la seccion. O hacerle update
         * @version 1.0
         */
        $scope.$on('seccionesBroadcast', function () {
          });
        vm.seccion.bibliotecaId = vm.idBiblioteca;


        /**
         * @author Maureen Leon
         * Obtiene el usuario que no es el jhi-user
         * @version 1.0
         */
        function obtenerUsuarioNormal(){
            PersonalizarCuenta.obtenerUsuarioNormal(vm.account.id).success(function(response){
                vm.usuario=response;
            }).error(function (error){

            });
        }

        /**
         * Cierra el modal
         */
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        /**
         * @author Eduardo Guerrero
         * Guarda la seccion en la biblioteca del usuario normal
         * EL NOMBRE NUNCA SE REPITE
         * @version 1.0
         */
        function save () {
            vm.isSaving = true;
            vm.seccion = {
                nombre: vm.seccion.nombre,
                id:null,
                bibliotecaId:vm.seccion.bibliotecaId
            };

            SeccionCustomService.agregarSeccion({seccion:vm.seccion},onSaveError()).then(function (data) {
                     NotificacionGeneral.notificacion('','La sección ha sido creada',1);
                     cargarSecciones();
                    $uibModalInstance.close(data);
                }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }else if(response.status === 400){
                    vm.errorDuplicado = true;
                }
            });
            vm.errorConnection = false;
            vm.errorDuplicado = false;
        }

        /**
         * @author Eduardo Guerrero
         * Actualiza la seccion que se esta deseando actualizar
         * Verifica que el nombre no lo tenga otra seccion
         * Si no lo tiene lo puede actualizar
         * EL NOMBRE NUNCA SE REPITE
         * @version 1.0
         */
        function update(){
            vm.isSaving = true;
            vm.errorDuplicado = false;
            vm.seccion = {
                nombre: vm.seccion.nombre,
                id:vm.seccion.id,
                bibliotecaId:vm.seccion.bibliotecaId
            };
            //Ciclo que verifica el nombre que se esta enviando por las secciones ya registradas
            for(var i = 0 ; i < vm.listaSeccion.length;i++){
                if(vm.listaSeccion[i].nombre == vm.seccion.nombre){
                    vm.comprobarNombre = true;
                    break;
                }else{
                   vm.comprobarNombre = false;
                }
            }

            if(vm.comprobarNombre == true){
                vm.errorDuplicado = true;
                onSaveError();
            }else{
                SeccionCustomService.actualizarSeccion({seccion:vm.seccion},onSaveError()).then(function (data) {
                    vm.isSaving = false;
                    NotificacionGeneral.notificacion('','La sección ha sido modificada',1);
                    $uibModalInstance.close(data);
                }).catch(function (response) {

                    if (response.status === 500) {
                        vm.errorConnection = true;
                    }
                });
            }
            vm.errorConnection = false;

        }

        /**
         * @author Eduardo Guerrero
         * Carga las secciones del usuario normal, obtiene la biblioteca y obtiene las secciones de esta
         * Se usa esta funcion para verificar el nombre en el update de la seccion
         * @version 1.0
         */
        function cargarSecciones() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                if (account != null) {
                    obtenerUsuarioNormal();
                    SeccionCustomService.obtenerBibliotecaPorJhiUser(vm.account.id).success(function (data) {
                        vm.idBiblioteca = data.id;
                        vm.bibliotecaActual = vm.idBiblioteca;
                        SeccionCustomService.listarSeccion(vm.idBiblioteca).success(function (data) {
                            vm.listaSeccion = data;
                        }).error(function () {
                        });
                    });
                }

            });
        }

        function onSaveError () {
            vm.isSaving = false;

        }



    }
})();
