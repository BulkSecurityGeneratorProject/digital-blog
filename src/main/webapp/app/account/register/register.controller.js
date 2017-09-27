(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = [ '$timeout', 'Auth', 'LoginService'];

    function RegisterController ($timeout, Auth, LoginService) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;

        $timeout(function (){angular.element('#login').focus();});

        //Metodo para iniciar el registro del usuario
        function register () {
            //Verifica que las contrasñeas sean iguales
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                //variables para validaciones
                vm.registerAccount.langKey =  'en' ;
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                //Succes es OK la cuenta fue creada.
                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    //atrapa las excepciones
                    //400 si el login esta en uso y el email tambien
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
