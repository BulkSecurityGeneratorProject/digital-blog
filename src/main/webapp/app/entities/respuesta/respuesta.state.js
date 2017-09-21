(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('respuesta', {
            parent: 'entity',
            url: '/respuesta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Respuestas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/respuesta/respuestas.html',
                    controller: 'RespuestaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('respuesta-detail', {
            parent: 'respuesta',
            url: '/respuesta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Respuesta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/respuesta/respuesta-detail.html',
                    controller: 'RespuestaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Respuesta', function($stateParams, Respuesta) {
                    return Respuesta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'respuesta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('respuesta-detail.edit', {
            parent: 'respuesta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-dialog.html',
                    controller: 'RespuestaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Respuesta', function(Respuesta) {
                            return Respuesta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('respuesta.new', {
            parent: 'respuesta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-dialog.html',
                    controller: 'RespuestaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contenido: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: 'respuesta' });
                }, function() {
                    $state.go('respuesta');
                });
            }]
        })
        .state('respuesta.edit', {
            parent: 'respuesta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-dialog.html',
                    controller: 'RespuestaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Respuesta', function(Respuesta) {
                            return Respuesta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: 'respuesta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('respuesta.delete', {
            parent: 'respuesta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-delete-dialog.html',
                    controller: 'RespuestaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Respuesta', function(Respuesta) {
                            return Respuesta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: 'respuesta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
