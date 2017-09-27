
(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('asignarColaboradoresController', asignarColaboradoresController);

    asignarColaboradoresController.$inject = ['$uibModalInstance','Categoria','Principal','ListarSeguidores','PersonalizarCuenta','ColaboradorServiceShare'];

    function asignarColaboradoresController ($uibModalInstance, Categoria,Principal,ListarSeguidores,PersonalizarCuenta,ColaboradorServiceShare) {
        var vm = this;

    }
})();
