(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publicacion', {
            parent: 'entity',
            url: '/publicacion',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Publicacions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacion/publicacions.html',
                    controller: 'PublicacionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('publicacion-detail', {
            parent: 'publicacion',
            url: '/publicacion/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Publicacion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacion/publicacion-detail.html',
                    controller: 'PublicacionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Publicacion', function($stateParams, Publicacion) {
                    return Publicacion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'publicacion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('publicacion-detail.edit', {
            parent: 'publicacion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion/publicacion-dialog.html',
                    controller: 'PublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publicacion', function(Publicacion) {
                            return Publicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacion.new', {
            parent: 'publicacion',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion/publicacion-dialog.html',
                    controller: 'PublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                urlImagen: null,
                                descripcion: null,
                                contenido: null,
                                tipo: null,
                                titulo: null,
                                estado: null,
                                cantidadIteraciones: null,
                                fotopublicacion: null,
                                fotopublicacionContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('publicacion', null, { reload: 'publicacion' });
                }, function() {
                    $state.go('publicacion');
                });
            }]
        })
        .state('publicacion.edit', {
            parent: 'publicacion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion/publicacion-dialog.html',
                    controller: 'PublicacionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publicacion', function(Publicacion) {
                            return Publicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacion', null, { reload: 'publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacion.delete', {
            parent: 'publicacion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion/publicacion-delete-dialog.html',
                    controller: 'PublicacionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Publicacion', function(Publicacion) {
                            return Publicacion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacion', null, { reload: 'publicacion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
