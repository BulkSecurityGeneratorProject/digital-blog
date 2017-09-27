(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
                // ,'sidenav@': {
                //     templateUrl: 'app/layouts/sidenav/sidenav.html',
                //     data:{
                //         css:'content/css/custom/sidenav.css',
                //         authorities: ['ROLE_USER'],
                //     },
                //     controller: 'SideNavController',
                //     controllerAs: 'vm'
                // }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });
    }
})();
