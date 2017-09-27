(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('suscripciones', {
            parent: 'entity',
            url: '/suscripciones',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Suscripciones'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suscripciones/suscripciones.html',
                    controller: 'SuscripcionesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('suscripciones-detail', {
            parent: 'suscripciones',
            url: '/suscripciones/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Suscripciones'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suscripciones/suscripciones-detail.html',
                    controller: 'SuscripcionesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Suscripciones', function($stateParams, Suscripciones) {
                    return Suscripciones.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'suscripciones',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('suscripciones-detail.edit', {
            parent: 'suscripciones-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suscripciones/suscripciones-dialog.html',
                    controller: 'SuscripcionesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suscripciones', function(Suscripciones) {
                            return Suscripciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suscripciones.new', {
            parent: 'suscripciones',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suscripciones/suscripciones-dialog.html',
                    controller: 'SuscripcionesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idCanal: null,
                                idSiguiendo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('suscripciones', null, { reload: 'suscripciones' });
                }, function() {
                    $state.go('suscripciones');
                });
            }]
        })
        .state('suscripciones.edit', {
            parent: 'suscripciones',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suscripciones/suscripciones-dialog.html',
                    controller: 'SuscripcionesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suscripciones', function(Suscripciones) {
                            return Suscripciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suscripciones', null, { reload: 'suscripciones' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suscripciones.delete', {
            parent: 'suscripciones',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suscripciones/suscripciones-delete-dialog.html',
                    controller: 'SuscripcionesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Suscripciones', function(Suscripciones) {
                            return Suscripciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suscripciones', null, { reload: 'suscripciones' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
