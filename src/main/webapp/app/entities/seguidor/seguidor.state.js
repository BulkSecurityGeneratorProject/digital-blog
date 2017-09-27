(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seguidor', {
            parent: 'entity',
            url: '/seguidor',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Seguidors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seguidor/seguidors.html',
                    controller: 'SeguidorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('seguidor-detail', {
            parent: 'seguidor',
            url: '/seguidor/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Seguidor'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seguidor/seguidor-detail.html',
                    controller: 'SeguidorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Seguidor', function($stateParams, Seguidor) {
                    return Seguidor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seguidor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seguidor-detail.edit', {
            parent: 'seguidor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguidor/seguidor-dialog.html',
                    controller: 'SeguidorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seguidor', function(Seguidor) {
                            return Seguidor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seguidor.new', {
            parent: 'seguidor',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguidor/seguidor-dialog.html',
                    controller: 'SeguidorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                estadoSeguidor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seguidor', null, { reload: 'seguidor' });
                }, function() {
                    $state.go('seguidor');
                });
            }]
        })
        .state('seguidor.edit', {
            parent: 'seguidor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguidor/seguidor-dialog.html',
                    controller: 'SeguidorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seguidor', function(Seguidor) {
                            return Seguidor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seguidor', null, { reload: 'seguidor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seguidor.delete', {
            parent: 'seguidor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguidor/seguidor-delete-dialog.html',
                    controller: 'SeguidorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Seguidor', function(Seguidor) {
                            return Seguidor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seguidor', null, { reload: 'seguidor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
