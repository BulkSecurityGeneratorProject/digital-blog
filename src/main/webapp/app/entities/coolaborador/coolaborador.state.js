(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('coolaborador', {
            parent: 'entity',
            url: '/coolaborador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coolaboradors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coolaborador/coolaboradors.html',
                    controller: 'CoolaboradorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('coolaborador-detail', {
            parent: 'coolaborador',
            url: '/coolaborador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Coolaborador'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coolaborador/coolaborador-detail.html',
                    controller: 'CoolaboradorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Coolaborador', function($stateParams, Coolaborador) {
                    return Coolaborador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'coolaborador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('coolaborador-detail.edit', {
            parent: 'coolaborador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coolaborador/coolaborador-dialog.html',
                    controller: 'CoolaboradorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coolaborador', function(Coolaborador) {
                            return Coolaborador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coolaborador.new', {
            parent: 'coolaborador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coolaborador/coolaborador-dialog.html',
                    controller: 'CoolaboradorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('coolaborador', null, { reload: 'coolaborador' });
                }, function() {
                    $state.go('coolaborador');
                });
            }]
        })
        .state('coolaborador.edit', {
            parent: 'coolaborador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coolaborador/coolaborador-dialog.html',
                    controller: 'CoolaboradorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coolaborador', function(Coolaborador) {
                            return Coolaborador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coolaborador', null, { reload: 'coolaborador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coolaborador.delete', {
            parent: 'coolaborador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coolaborador/coolaborador-delete-dialog.html',
                    controller: 'CoolaboradorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Coolaborador', function(Coolaborador) {
                            return Coolaborador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coolaborador', null, { reload: 'coolaborador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
