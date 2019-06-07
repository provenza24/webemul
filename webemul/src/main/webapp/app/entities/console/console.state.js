(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('console', {
            parent: 'entity',
            url: '/console',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Consoles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/console/consoles.html',
                    controller: 'ConsoleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('console-detail', {
            parent: 'console',
            url: '/console/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Console'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/console/console-detail.html',
                    controller: 'ConsoleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Console', function($stateParams, Console) {
                    return Console.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'console',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('console-detail.edit', {
            parent: 'console-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/console/console-dialog.html',
                    controller: 'ConsoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Console', function(Console) {
                            return Console.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('console.new', {
            parent: 'console',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/console/console-dialog.html',
                    controller: 'ConsoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                abbreviation: null,
                                name: null,
                                pathIcon: null,
                                pathControllerIcon: null,
                                manufacturer: null,
                                generation: null,
                                bits: null,
                                resume: null,
                                pathRomsFolder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('console', null, { reload: 'console' });
                }, function() {
                    $state.go('console');
                });
            }]
        })
        .state('console.edit', {
            parent: 'console',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/console/console-dialog.html',
                    controller: 'ConsoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Console', function(Console) {
                            return Console.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('console', null, { reload: 'console' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('console.delete', {
            parent: 'console',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/console/console-delete-dialog.html',
                    controller: 'ConsoleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Console', function(Console) {
                            return Console.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('console', null, { reload: 'console' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
