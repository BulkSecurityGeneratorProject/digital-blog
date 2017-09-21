(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'app',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: 'Error page!'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'app',
                url: '/accessdenied',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/accessdenied.html'
                    }
                }
            })
            .state('notFound', {
            parent: 'app',
            url: '/notFound',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/error/notFound.html'
                }
            }
        })
            .state('error500', {
                parent: 'app',
                url: '/error500',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error500.html'
                    }
                }
            });
    }
})();
