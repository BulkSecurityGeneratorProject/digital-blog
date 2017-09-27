(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PersonalizarPerfilCustomController',PersonalizarPerfilCustomController);
    PersonalizarPerfilCustomController.$inject=['$scope','DataUtils','Usuario','PersonalizarCuenta','Principal','NotificacionGeneral','$timeout','$state','DateUtils'];
    function PersonalizarPerfilCustomController($scope,DataUtils,Usuario,PersonalizarCuenta,Principal,NotificacionGeneral,$timeout,$state,DateUtils) {

        var vm = this;
        vm.setfoto=false;
        vm.errorConection =  false;
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.fechaNacimiento = false;
        vm.openCalendar = openCalendar;
        obtenerUsuarioNormal();
        vm.invalidBornDate =  false;

        /**
         * @author Maureen Leon
         * Obtiene la cuenta logueada y envia el JHIUser para obtener el usuario
         * @version 1.0
         */
        function obtenerUsuarioNormal(){
            Principal.identity().then(function(account) {
                vm.account = account;
                PersonalizarCuenta.obtenerUsuarioNormal(account.id).success(function(response){
                    response = angular.fromJson(response);
                    response.fechaNacimiento = DateUtils.convertDateTimeFromServer(response.fechaNacimiento);
                    vm.usuario=response;
                }).error(function (error){
                    console.log("Problema obtenerUsuarioNormal");
                });
            });
        }

        /**
         * @author Maureen Leon
         * Hace el update del usuario
         * @version 1.0
         */
        vm.save= function () {
            if (vm.usuario.id !== null) {
                vm.usuario.estado=true;
                obtenerEdad();
                if(vm.invalidBornDate == false){
                    PersonalizarCuenta.update(vm.usuario).success(function (response) {
                        response.fechaNacimiento = DateUtils.convertDateTimeFromServer(response.fechaNacimiento);
                        vm.usuario = response;
                        NotificacionGeneral.notificacion('Listo!','Su información se ha guardado',1);
                        $timeout(function(){
                        },5000);
                        $state.go('home');
                    }).error(function (error) {
                        if(error.description == "Internal server error"){
                            vm.errorConection = true;
                        }
                        console.log("Problema inesperado al hacer update ");
                    });
                }

            }
        }

        /**
         * @author Maureen Leon
         * setea la foto del perfil
         * @param $file imagen del usuario
         * @version 1.0
         */
        vm.setFotoperfil = function ($file) {
            vm.setfoto=true;
            if ($file && $file.$error === 'pattern') {
                 return;
             }
             if ($file) {
                 DataUtils.toBase64($file, function (base64Data) {
                     $scope.$apply(function () {
                        vm.usuario.fotoperfil = base64Data;
                     });
                 });
             }
        };



        /**
         * @author Maureen Leon
         * Abre el calendario
         * @param date
         * @version 1.0
         */
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }


        /**
         * @author Maureen Leon
         * Calcula la edad
         * @version 1.0
         */
        function obtenerEdad(){
            var currentDate =  new Date();
            var bornDate =  vm.usuario.fechaNacimiento;

            //fecha actual
            var currentYear,currentDay,currentMonth;
            currentYear = currentDate.getFullYear();
            currentDay  = currentDate.getDay();
            currentMonth = currentDate.getMonth();


            // fecha de nacimiento
            var bornYear,bornDay,bornMonth;
            bornYear = bornDate.getFullYear();
            bornDay = bornDate.getDay();
            bornMonth= bornDate.getMonth();

            if(bornYear > 2010){
                vm.invalidBornDate = true;
            }else if(bornYear < 2010){
                vm.invalidBornDate =  false;
            }


                // realizamos el calculo
                var edad = (currentYear + 1900) - bornYear;
                if ( currentMonth < bornMonth )
                {
                    edad--;
                }
                if ((bornMonth == currentMonth) && (currentDay < bornDay))
                {
                    edad--;
                }
                if (edad > 1900)
                {
                    edad -= 1900;
                }

                // calculamos los meses
                var meses=0;
                if(currentMonth>bornMonth)
                    meses=currentMonth-bornMonth;
                if(currentMonth<bornMonth)
                    meses=12-(bornMonth-currentMonth);
                if(currentMonth==bornMonth && bornDay>currentDay)
                    meses=11;

                // calculamos los dias
                var dias=0;
                if(currentDay>bornDay)
                    dias=currentDay-bornDay;
                if(currentDay<bornDay)
                {
                    var ultimoDiaMes=new Date(currentYear, currentMonth, 0);
                    dias=ultimoDiaMes.getDate()-(bornDay-currentDay);
                }

                vm.usuario.edad = edad;
        }
    }


})();
