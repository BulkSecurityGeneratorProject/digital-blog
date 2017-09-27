(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('biblioteca', {
            parent: 'entity',
            url: '/biblioteca',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Bibliotecas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/biblioteca/bibliotecas.html',
                    controller: 'BibliotecaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('biblioteca-detail', {
            parent: 'biblioteca',
            url: '/biblioteca/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Biblioteca'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/biblioteca/biblioteca-detail.html',
                    controller: 'BibliotecaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Biblioteca', function($stateParams, Biblioteca) {
                    return Biblioteca.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'biblioteca',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('biblioteca-detail.edit', {
            parent: 'biblioteca-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biblioteca/biblioteca-dialog.html',
                    controller: 'BibliotecaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Biblioteca', function(Biblioteca) {
                            return Biblioteca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('biblioteca.new', {
            parent: 'biblioteca',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biblioteca/biblioteca-dialog.html',
                    controller: 'BibliotecaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idSeccion: null,
                                idJhiUser: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('biblioteca', null, { reload: 'biblioteca' });
                }, function() {
                    $state.go('biblioteca');
                });
            }]
        })
        .state('biblioteca.edit', {
            parent: 'biblioteca',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biblioteca/biblioteca-dialog.html',
                    controller: 'BibliotecaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Biblioteca', function(Biblioteca) {
                            return Biblioteca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('biblioteca', null, { reload: 'biblioteca' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('biblioteca.delete', {
            parent: 'biblioteca',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/biblioteca/biblioteca-delete-dialog.html',
                    controller: 'BibliotecaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Biblioteca', function(Biblioteca) {
                            return Biblioteca.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('biblioteca', null, { reload: 'biblioteca' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
