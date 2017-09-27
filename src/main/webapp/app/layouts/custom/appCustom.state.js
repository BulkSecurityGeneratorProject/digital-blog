(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('appCustom', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbarCustom/navbarCustom.html',
                    controller: 'NavbarCustomController',
                    controllerAs: 'vm',
                    data:{
                        css:'content/css/custom/navbar.css'
                    }
                },
                /*'sidenav@': {
                    templateUrl: 'app/layouts/sidenav/sidenav.html',
                    data:{
                        css:'content/css/custom/sidenav.css'
                    },
                    controller: 'SideNavController',
                    controllerAs: 'vm'
                }*/
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
