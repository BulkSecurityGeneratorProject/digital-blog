(function () {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['toastr', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'webSocketService', '$scope', 'PersonalizarCuenta'];

    function NavbarController(toastr, $state, Auth, Principal, ProfileService, LoginService, webSocketService, $scope, PersonalizarCuenta) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        })
        getAccount();

        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
        vm.isAuthenticated = Principal.isAuthenticated;

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                obtenerUsuarioNormal();
            });
        }

        function obtenerUsuarioNormal() {
            PersonalizarCuenta.obtenerUsuarioNormal(vm.account.id).success(function (response) {
                vm.usuario = response;
                receiveWS();
                obtenerNotificcionesNoLeidas();
            })
        }


        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.obtenerUsuarioActual = obtenerUsuarioActual;

        vm.$state = $state;
        vm.usuario;
        vm.notificationCounter = 0
        /**
         * captura las notificaciones y aumenta el contador para que se muestre el número en el ícono de campana
         */

       function receiveWS(){
            webSocketService.receive().then(null, null, function (activity) {
                if (activity.idUsuario != 0) {
                    // if (activity.idUsuario != vm.usuario.id) {
                    vm.notificationCounter++;
                    showActivity(activity);
                    //}
                }

            });
        }

        /**
         * muestar la notificacion
         * @param activity
         */
        function showActivity(activity) {

            toastr.info(activity.descripcion, "DigitalBLog", {
                preventDuplicates: true,
                timeOut: 5000,
                progressBar: true,
                onTap: function () {
                    $state.go('listarNotificaciones');
                    vm.notificationCounter = 0;
                }
            });

        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            vm.account = null;
            Auth.logout();
            $state.go('home');
            webSocketService.disconnect();
            webSocketService.unsubscribe();

        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function obtenerUsuarioActual() {
            Principal.identity().then(function (account) {
                vm.usuario = account;
            });
        }

        function obtenerNotificcionesNoLeidas(){
            PersonalizarCuenta.obtenerNotificcionesNoLeidas({id:vm.usuario.id}).success(function (response){
                vm.notificationCounter=response;
            });
        }

    }
})();
