(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .controller('PublicacionReportadaDetailController', PublicacionReportadaDetailController);

    PublicacionReportadaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PublicacionReportada'];

    function PublicacionReportadaDetailController($scope, $rootScope, $stateParams, previousState, entity, PublicacionReportada) {
        var vm = this;

        vm.publicacionReportada = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('digitalBlogApp:publicacionReportadaUpdate', function(event, result) {
            vm.publicacionReportada = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
