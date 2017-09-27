(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('imagen-por-publicacion', {
            parent: 'entity',
            url: '/imagen-por-publicacion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImagenPorPublicacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacions.html',
                    controller: 'ImagenPorPublicacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('imagen-por-publicacion-detail', {
            parent: 'imagen-por-publicacion',
            url: '/imagen-por-publicacion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImagenPorPublicacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacion-detail.html',
                    controller: 'ImagenPorPublicacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ImagenPorPublicacion', function($stateParams, ImagenPorPublicacion) {
                    return ImagenPorPublicacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'imagen-por-publicacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('imagen-por-publicacion-detail.edit', {
            parent: 'imagen-por-publicacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacion-dialog.html',
                    controller: 'ImagenPorPublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ImagenPorPublicacion', function(ImagenPorPublicacion) {
                            return ImagenPorPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('imagen-por-publicacion.new', {
            parent: 'imagen-por-publicacion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacion-dialog.html',
                    controller: 'ImagenPorPublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idPublicacion: null,
                                imagen: null,
                                imagenContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('imagen-por-publicacion', null, { reload: 'imagen-por-publicacion' });
                }, function() {
                    $state.go('imagen-por-publicacion');
                });
            }]
        })
        .state('imagen-por-publicacion.edit', {
            parent: 'imagen-por-publicacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacion-dialog.html',
                    controller: 'ImagenPorPublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ImagenPorPublicacion', function(ImagenPorPublicacion) {
                            return ImagenPorPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imagen-por-publicacion', null, { reload: 'imagen-por-publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('imagen-por-publicacion.delete', {
            parent: 'imagen-por-publicacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imagen-por-publicacion/imagen-por-publicacion-delete-dialog.html',
                    controller: 'ImagenPorPublicacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ImagenPorPublicacion', function(ImagenPorPublicacion) {
                            return ImagenPorPublicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imagen-por-publicacion', null, { reload: 'imagen-por-publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
