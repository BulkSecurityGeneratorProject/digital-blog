(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('canal', {
            parent: 'entity',
            url: '/canal',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Canals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/canal/canals.html',
                    controller: 'CanalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('canal-detail', {
            parent: 'canal',
            url: '/canal/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Canal'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/canal/canal-detail.html',
                    controller: 'CanalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Canal', function($stateParams, Canal) {
                    return Canal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'canal',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('canal-detail.edit', {
            parent: 'canal-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canal/canal-dialog.html',
                    controller: 'CanalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Canal', function(Canal) {
                            return Canal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('canal.new', {
            parent: 'canal',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canal/canal-dialog.html',
                    controller: 'CanalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idUsuario: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('canal', null, { reload: 'canal' });
                }, function() {
                    $state.go('canal');
                });
            }]
        })
        .state('canal.edit', {
            parent: 'canal',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canal/canal-dialog.html',
                    controller: 'CanalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Canal', function(Canal) {
                            return Canal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('canal', null, { reload: 'canal' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('canal.delete', {
            parent: 'canal',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/canal/canal-delete-dialog.html',
                    controller: 'CanalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Canal', function(Canal) {
                            return Canal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('canal', null, { reload: 'canal' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
