(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('nota', {
            parent: 'entity',
            url: '/nota',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Notas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nota/notas.html',
                    controller: 'NotaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('nota-detail', {
            parent: 'nota',
            url: '/nota/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Nota'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nota/nota-detail.html',
                    controller: 'NotaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Nota', function($stateParams, Nota) {
                    return Nota.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'nota',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('nota-detail.edit', {
            parent: 'nota-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nota/nota-dialog.html',
                    controller: 'NotaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Nota', function(Nota) {
                            return Nota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nota.new', {
            parent: 'nota',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nota/nota-dialog.html',
                    controller: 'NotaDialogController',
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
                    $state.go('nota', null, { reload: 'nota' });
                }, function() {
                    $state.go('nota');
                });
            }]
        })
        .state('nota.edit', {
            parent: 'nota',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nota/nota-dialog.html',
                    controller: 'NotaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Nota', function(Nota) {
                            return Nota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nota', null, { reload: 'nota' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nota.delete', {
            parent: 'nota',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nota/nota-delete-dialog.html',
                    controller: 'NotaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Nota', function(Nota) {
                            return Nota.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nota', null, { reload: 'nota' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
