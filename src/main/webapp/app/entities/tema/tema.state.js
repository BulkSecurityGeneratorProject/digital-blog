(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tema', {
            parent: 'entity',
            url: '/tema',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Temas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema/temas.html',
                    controller: 'TemaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tema-detail', {
            parent: 'tema',
            url: '/tema/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tema'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema/tema-detail.html',
                    controller: 'TemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tema', function($stateParams, Tema) {
                    return Tema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tema-detail.edit', {
            parent: 'tema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema/tema-dialog.html',
                    controller: 'TemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tema', function(Tema) {
                            return Tema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema.new', {
            parent: 'tema',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema/tema-dialog.html',
                    controller: 'TemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tema', null, { reload: 'tema' });
                }, function() {
                    $state.go('tema');
                });
            }]
        })
        .state('tema.edit', {
            parent: 'tema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema/tema-dialog.html',
                    controller: 'TemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tema', function(Tema) {
                            return Tema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema', null, { reload: 'tema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema.delete', {
            parent: 'tema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema/tema-delete-dialog.html',
                    controller: 'TemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tema', function(Tema) {
                            return Tema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema', null, { reload: 'tema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
