(function() {
    'use strict';

    angular
        .module('digitalBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publicacion-reportada', {
            parent: 'entity',
            url: '/publicacion-reportada',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'PublicacionReportadas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportadas.html',
                    controller: 'PublicacionReportadaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('publicacion-reportada-detail', {
            parent: 'publicacion-reportada',
            url: '/publicacion-reportada/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'PublicacionReportada'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportada-detail.html',
                    controller: 'PublicacionReportadaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PublicacionReportada', function($stateParams, PublicacionReportada) {
                    return PublicacionReportada.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'publicacion-reportada',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('publicacion-reportada-detail.edit', {
            parent: 'publicacion-reportada-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportada-dialog.html',
                    controller: 'PublicacionReportadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PublicacionReportada', function(PublicacionReportada) {
                            return PublicacionReportada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacion-reportada.new', {
            parent: 'publicacion-reportada',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportada-dialog.html',
                    controller: 'PublicacionReportadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cantidadReportes: null,
                                idPublicacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('publicacion-reportada', null, { reload: 'publicacion-reportada' });
                }, function() {
                    $state.go('publicacion-reportada');
                });
            }]
        })
        .state('publicacion-reportada.edit', {
            parent: 'publicacion-reportada',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportada-dialog.html',
                    controller: 'PublicacionReportadaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PublicacionReportada', function(PublicacionReportada) {
                            return PublicacionReportada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacion-reportada', null, { reload: 'publicacion-reportada' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacion-reportada.delete', {
            parent: 'publicacion-reportada',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacion-reportada/publicacion-reportada-delete-dialog.html',
                    controller: 'PublicacionReportadaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PublicacionReportada', function(PublicacionReportada) {
                            return PublicacionReportada.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacion-reportada', null, { reload: 'publicacion-reportada' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
