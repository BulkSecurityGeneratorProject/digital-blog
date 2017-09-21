(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('temaCustom', {
            parent: 'digitalBlog',
            url: '/temaCustom',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Temas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/custom/tema/temasCustom.html',
                    controller: 'TemaCustomController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        id: null,
                        nombre: null
                    };
                }
            }
        })
    }

})();
