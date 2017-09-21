/**
 * Created by jose_ on 9/4/2017.
 */
(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('ConfigCompartidaDialogController', ConfigCompartidaDialogController);

    ConfigCompartidaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance','CompartidaServiceShare'];

    function ConfigCompartidaDialogController ($timeout, $scope, $stateParams, $uibModalInstance,CompartidaServiceShare) {
        var vm = this;

        vm.clear = clear;

        function clear (result) {
            CompartidaServiceShare.enviarSiONo(result);
            $uibModalInstance.dismiss('cancel');
        }
    }
})();

