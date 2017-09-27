(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','PersonalizarCuenta','webSocketService','toastr'];

    function HomeController ($scope, Principal, LoginService, $state,PersonalizarCuenta,webSocketService,toastr) {
        var vm = this;
        vm.usuario=null;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        function verPerfil() {
             $state.go('verPerfil('+vm.getAccount()+')');
        }
        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                if(vm.account!=null) {
                    obtenerCuenta();
                    vm.isAuthenticated = Principal.isAuthenticated;
                }
            });
        }

        function register () {
            $state.go('register');
        }

        function obtenerCuenta(){

            if (vm.account.authorities[0]=="ROLE_USER") {
                PersonalizarCuenta.obtenerUsuarioNormal(vm.account.id).success(function (response) {
                    vm.usuario = response;
                    connectWebSocket();
                    redireccionar();
                }).error(function (error) {
                });
            }
        }

        function redireccionar () {

            if(vm.usuario.estado == false){
                $state.go('personalizarPerfilCustom');
            }else{
                $state.go('home');
            }
        }
        // /**
        //  * captura las notificaciones
        //  * llama a show activity
        //  */
        //  webSocketService.receive().then(null, null, function(activity) {
        //     if(activity.idUsuario!=0){
        //         showActivity(activity);
        //     }
        //
        // });
        // /**
        //  * muestar la notificacion
        //  * @param activity
        //  */
        // function showActivity(activity){
        //     toastr.info(activity.descripcion,"DigitalBLog",{
        //         preventDuplicates: true,
        //         timeOut: 5000,
        //         progressBar: true
        //     });
        // }
        /**
         * obtiene el usuario para poder subscribirse subscribirse a su topic personal con su id
         */
        function connectWebSocket(){
                webSocketService.connect(vm.usuario.id);
                // webSocketService.subscribe(vm.usuario.id);
            };
    }
})();




