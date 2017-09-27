(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seccion-publicacion', {
            parent: 'entity',
            url: '/seccion-publicacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SeccionPublicacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacions.html',
                    controller: 'SeccionPublicacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('seccion-publicacion-detail', {
            parent: 'seccion-publicacion',
            url: '/seccion-publicacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SeccionPublicacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacion-detail.html',
                    controller: 'SeccionPublicacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SeccionPublicacion', function($stateParams, SeccionPublicacion) {
                    return SeccionPublicacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seccion-publicacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seccion-publicacion-detail.edit', {
            parent: 'seccion-publicacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacion-dialog.html',
                    controller: 'SeccionPublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SeccionPublicacion', function(SeccionPublicacion) {
                            return SeccionPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seccion-publicacion.new', {
            parent: 'seccion-publicacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacion-dialog.html',
                    controller: 'SeccionPublicacionDialogController',
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
                    $state.go('seccion-publicacion', null, { reload: 'seccion-publicacion' });
                }, function() {
                    $state.go('seccion-publicacion');
                });
            }]
        })
        .state('seccion-publicacion.edit', {
            parent: 'seccion-publicacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacion-dialog.html',
                    controller: 'SeccionPublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SeccionPublicacion', function(SeccionPublicacion) {
                            return SeccionPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seccion-publicacion', null, { reload: 'seccion-publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seccion-publicacion.delete', {
            parent: 'seccion-publicacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion-publicacion/seccion-publicacion-delete-dialog.html',
                    controller: 'SeccionPublicacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SeccionPublicacion', function(SeccionPublicacion) {
                            return SeccionPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seccion-publicacion', null, { reload: 'seccion-publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
