(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('SeccionCustomDeleteController',SeccionDeleteController);

    SeccionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seccion','NotificacionGeneral','SeccionCustomService'];

    function SeccionDeleteController($uibModalInstance, entity, Seccion,NotificacionGeneral,SeccionCustomService) {
        var vm = this;

        vm.seccion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        vm.errorConnection =false;

        /**
         * Cierra el modal
         */
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        /**
         * @author Eduardo Guerrero
         * Confirma si se desea eliminar la seccion seleccionada
         * @param id de la seccion a eliminar
         * @version 1.0
         */

        function confirmDelete(id) {
            SeccionCustomService.eliminarSeccion(id).success(function () {
                NotificacionGeneral.notificacion('','La sección ha sido eliminada',1);
                $uibModalInstance.close(true);
            }).catch(function (response) {
                if (response.status === 500) {
                    vm.errorConnection = true;
                }
            });
            vm.errorConnection = false;
        }
    }
})();
