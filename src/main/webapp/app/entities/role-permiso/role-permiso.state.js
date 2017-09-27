(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('role-permiso', {
            parent: 'entity',
            url: '/role-permiso',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RolePermisos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role-permiso/role-permisos.html',
                    controller: 'RolePermisoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('role-permiso-detail', {
            parent: 'role-permiso',
            url: '/role-permiso/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RolePermiso'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/role-permiso/role-permiso-detail.html',
                    controller: 'RolePermisoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RolePermiso', function($stateParams, RolePermiso) {
                    return RolePermiso.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'role-permiso',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('role-permiso-detail.edit', {
            parent: 'role-permiso-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-permiso/role-permiso-dialog.html',
                    controller: 'RolePermisoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RolePermiso', function(RolePermiso) {
                            return RolePermiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('role-permiso.new', {
            parent: 'role-permiso',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-permiso/role-permiso-dialog.html',
                    controller: 'RolePermisoDialogController',
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
                    $state.go('role-permiso', null, { reload: 'role-permiso' });
                }, function() {
                    $state.go('role-permiso');
                });
            }]
        })
        .state('role-permiso.edit', {
            parent: 'role-permiso',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-permiso/role-permiso-dialog.html',
                    controller: 'RolePermisoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RolePermiso', function(RolePermiso) {
                            return RolePermiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('role-permiso', null, { reload: 'role-permiso' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('role-permiso.delete', {
            parent: 'role-permiso',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/role-permiso/role-permiso-delete-dialog.html',
                    controller: 'RolePermisoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RolePermiso', function(RolePermiso) {
                            return RolePermiso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('role-permiso', null, { reload: 'role-permiso' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
