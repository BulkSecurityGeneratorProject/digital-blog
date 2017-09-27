(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaDialogCustomController', PublicacionReportadaDialogCustomController);

    PublicacionReportadaDialogCustomController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'PublicacionReportadaCustom', 'BibliotecaGeneralServiceShare', 'PublicacionReportada','NotificacionGeneral'];

    function PublicacionReportadaDialogCustomController($timeout, $scope, $stateParams, $uibModalInstance, PublicacionReportadaCustom, BibliotecaGeneralServiceShare, PublicacionReportada,NotificacionGeneral) {
        var vm = this;

        vm.publicacionReportada = {};
        vm.clear = clear;
        vm.save = save;
        vm.publicacion = {};

        $scope.$on('bibliotecaGeneralBroadcast', function () {
        });

        vm.publicacion = BibliotecaGeneralServiceShare.publicacion;

        console.log(vm.publicacion);
        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        /**
         * busca las publicaciones reportadas por el id de publicacion que se le esta enviando desde la biblioteca general
         */
        PublicacionReportadaCustom.cargarPublicacionReportadaPorIdPublicacion(vm.publicacion.id).success(function (data) {
            vm.publicacionD = data;
        })
        /**
         * Si la publicacion enviada es igual al id de la publicacion reportada se hace un update y el contador de reportes aumenta en 1 y asi constantemente
         * Si no guarda un nuevo reporte de la publicacion.
         */
        function save() {
            if (vm.publicacionD.idPublicacion == vm.publicacion.id) {
                vm.publicacionD.cantidadReportes = vm.publicacionD.cantidadReportes + 1;
                PublicacionReportada.update(vm.publicacionD);
                $uibModalInstance.close(true);
                NotificacionGeneral.notificacion('', 'Se reporto ' + vm.publicacion .titulo, 1);
            } else {
                vm.publicacionReportada = {
                    cantidadReportes: 1,
                    idPublicacion: vm.publicacion.id
                }
                PublicacionReportada.save(vm.publicacionReportada);
                $uibModalInstance.close(true);
                NotificacionGeneral.notificacion('', 'Se reporto ' + vm.publicacion .titulo, 1);
            }
        }
    }
})();
