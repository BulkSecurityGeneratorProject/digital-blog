(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('capitulo', {
            parent: 'entity',
            url: '/capitulo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Capitulos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/capitulo/capitulos.html',
                    controller: 'CapituloController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('capitulo-detail', {
            parent: 'capitulo',
            url: '/capitulo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Capitulo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/capitulo/capitulo-detail.html',
                    controller: 'CapituloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Capitulo', function($stateParams, Capitulo) {
                    return Capitulo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'capitulo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('capitulo-detail.edit', {
            parent: 'capitulo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capitulo/capitulo-dialog.html',
                    controller: 'CapituloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Capitulo', function(Capitulo) {
                            return Capitulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('capitulo.new', {
            parent: 'capitulo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capitulo/capitulo-dialog.html',
                    controller: 'CapituloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numeroCapitulo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('capitulo', null, { reload: 'capitulo' });
                }, function() {
                    $state.go('capitulo');
                });
            }]
        })
        .state('capitulo.edit', {
            parent: 'capitulo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capitulo/capitulo-dialog.html',
                    controller: 'CapituloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Capitulo', function(Capitulo) {
                            return Capitulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('capitulo', null, { reload: 'capitulo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('capitulo.delete', {
            parent: 'capitulo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/capitulo/capitulo-delete-dialog.html',
                    controller: 'CapituloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Capitulo', function(Capitulo) {
                            return Capitulo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('capitulo', null, { reload: 'capitulo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
