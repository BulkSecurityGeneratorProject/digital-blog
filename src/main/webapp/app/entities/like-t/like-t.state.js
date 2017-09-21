(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('like-t', {
            parent: 'entity',
            url: '/like-t',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LikeTS'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/like-t/like-ts.html',
                    controller: 'LikeTController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('like-t-detail', {
            parent: 'like-t',
            url: '/like-t/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LikeT'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/like-t/like-t-detail.html',
                    controller: 'LikeTDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LikeT', function($stateParams, LikeT) {
                    return LikeT.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'like-t',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('like-t-detail.edit', {
            parent: 'like-t-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/like-t/like-t-dialog.html',
                    controller: 'LikeTDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LikeT', function(LikeT) {
                            return LikeT.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('like-t.new', {
            parent: 'like-t',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/like-t/like-t-dialog.html',
                    controller: 'LikeTDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cantidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('like-t', null, { reload: 'like-t' });
                }, function() {
                    $state.go('like-t');
                });
            }]
        })
        .state('like-t.edit', {
            parent: 'like-t',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/like-t/like-t-dialog.html',
                    controller: 'LikeTDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LikeT', function(LikeT) {
                            return LikeT.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('like-t', null, { reload: 'like-t' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('like-t.delete', {
            parent: 'like-t',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/like-t/like-t-delete-dialog.html',
                    controller: 'LikeTDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LikeT', function(LikeT) {
                            return LikeT.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('like-t', null, { reload: 'like-t' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
