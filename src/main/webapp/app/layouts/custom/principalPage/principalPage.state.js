(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('principalPage', {
            parent: 'app',
            url: '/principalPage',
            data: {
                css:'content/css/custom/principalPage.css',
                authorities: ['ROLE_USER']

            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/principalPage/principalPage.html',
                    controller: 'PrincipalPageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
