/**
 * Created by jose_ on 25/3/2017.
 */
/**
 * Created by jgm16 on 04/03/2017.
 */
(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .service('NotificacionGeneral', NotificacionGeneral);

    NotificacionGeneral.$inject = ['toastr'];

    function NotificacionGeneral(toastr) {

        /**
         * @author José Nuñez
         * Funcion del service para notificaciones
         * @param titulo
         * @param textNotificar
         * @param tipo
         * @returns {*}
         * @Example NotificacionGeneralPrueba.notificacion('Felicidades','Su publicación ha sido publicada',1);
         * @version 1.0
         */
        this.notificacion = function(titulo,textNotificar,tipo){
            toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": true,
                "positionClass": "toast-bottom-left",
                "preventDuplicates": true,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            };

            switch (tipo) {
                case 1:
                    toastr.success(textNotificar,titulo);
                    break;
                case 2:
                    toastr.error(textNotificar,titulo);
                    break;
                case 3:
                    toastr.warning(textNotificar,titulo);
                    break;
            }
            return toastr;
        }

    }

})();
