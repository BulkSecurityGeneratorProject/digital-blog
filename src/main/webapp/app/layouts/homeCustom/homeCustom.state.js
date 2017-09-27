(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('digitalBlog', {
            parent: 'appCustom',
            url: '/digitalBlog',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/homeCustom/homeCustom.html',
                    controller: 'HomeCustomController',
                    controllerAs: 'vm',
                    data:{
                        css:'../../../content/css/custom/home.css'
                    }
                }
            }
        });
    }
})();
