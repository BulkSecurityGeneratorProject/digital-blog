(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('permiso', {
            parent: 'entity',
            url: '/permiso',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Permisos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/permiso/permisos.html',
                    controller: 'PermisoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('permiso-detail', {
            parent: 'permiso',
            url: '/permiso/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Permiso'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/permiso/permiso-detail.html',
                    controller: 'PermisoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Permiso', function($stateParams, Permiso) {
                    return Permiso.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'permiso',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('permiso-detail.edit', {
            parent: 'permiso-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/permiso/permiso-dialog.html',
                    controller: 'PermisoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Permiso', function(Permiso) {
                            return Permiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('permiso.new', {
            parent: 'permiso',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/permiso/permiso-dialog.html',
                    controller: 'PermisoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('permiso', null, { reload: 'permiso' });
                }, function() {
                    $state.go('permiso');
                });
            }]
        })
        .state('permiso.edit', {
            parent: 'permiso',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/permiso/permiso-dialog.html',
                    controller: 'PermisoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Permiso', function(Permiso) {
                            return Permiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('permiso', null, { reload: 'permiso' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('permiso.delete', {
            parent: 'permiso',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/permiso/permiso-delete-dialog.html',
                    controller: 'PermisoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Permiso', function(Permiso) {
                            return Permiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('permiso', null, { reload: 'permiso' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
