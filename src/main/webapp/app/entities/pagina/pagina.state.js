(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pagina', {
            parent: 'entity',
            url: '/pagina',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Paginas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pagina/paginas.html',
                    controller: 'PaginaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('pagina-detail', {
            parent: 'pagina',
            url: '/pagina/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pagina'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pagina/pagina-detail.html',
                    controller: 'PaginaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pagina', function($stateParams, Pagina) {
                    return Pagina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pagina',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pagina-detail.edit', {
            parent: 'pagina-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagina/pagina-dialog.html',
                    controller: 'PaginaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pagina', function(Pagina) {
                            return Pagina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pagina.new', {
            parent: 'pagina',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagina/pagina-dialog.html',
                    controller: 'PaginaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contenido: null,
                                numeroPagina: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pagina', null, { reload: 'pagina' });
                }, function() {
                    $state.go('pagina');
                });
            }]
        })
        .state('pagina.edit', {
            parent: 'pagina',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagina/pagina-dialog.html',
                    controller: 'PaginaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pagina', function(Pagina) {
                            return Pagina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pagina', null, { reload: 'pagina' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pagina.delete', {
            parent: 'pagina',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pagina/pagina-delete-dialog.html',
                    controller: 'PaginaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pagina', function(Pagina) {
                            return Pagina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pagina', null, { reload: 'pagina' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
