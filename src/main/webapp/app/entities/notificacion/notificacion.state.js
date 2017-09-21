(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('notificacion', {
            parent: 'entity',
            url: '/notificacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Notificacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/notificacion/notificacions.html',
                    controller: 'NotificacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('notificacion-detail', {
            parent: 'notificacion',
            url: '/notificacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Notificacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/notificacion/notificacion-detail.html',
                    controller: 'NotificacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Notificacion', function($stateParams, Notificacion) {
                    return Notificacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'notificacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('notificacion-detail.edit', {
            parent: 'notificacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notificacion/notificacion-dialog.html',
                    controller: 'NotificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Notificacion', function(Notificacion) {
                            return Notificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('notificacion.new', {
            parent: 'notificacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notificacion/notificacion-dialog.html',
                    controller: 'NotificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                tipo: null,
                                idUsuario: null,
                                link: null,
                                estado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('notificacion', null, { reload: 'notificacion' });
                }, function() {
                    $state.go('notificacion');
                });
            }]
        })
        .state('notificacion.edit', {
            parent: 'notificacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notificacion/notificacion-dialog.html',
                    controller: 'NotificacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Notificacion', function(Notificacion) {
                            return Notificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('notificacion', null, { reload: 'notificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('notificacion.delete', {
            parent: 'notificacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notificacion/notificacion-delete-dialog.html',
                    controller: 'NotificacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Notificacion', function(Notificacion) {
                            return Notificacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('notificacion', null, { reload: 'notificacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
