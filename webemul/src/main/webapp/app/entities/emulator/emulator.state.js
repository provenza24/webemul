(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emulator', {
            parent: 'entity',
            url: '/emulator',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Emulators'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emulator/emulators.html',
                    controller: 'EmulatorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('emulator-detail', {
            parent: 'emulator',
            url: '/emulator/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Emulator'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emulator/emulator-detail.html',
                    controller: 'EmulatorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Emulator', function($stateParams, Emulator) {
                    return Emulator.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emulator',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emulator-detail.edit', {
            parent: 'emulator-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emulator/emulator-dialog.html',
                    controller: 'EmulatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emulator', function(Emulator) {
                            return Emulator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emulator.new', {
            parent: 'emulator',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emulator/emulator-dialog.html',
                    controller: 'EmulatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                version: null,
                                pathIcon: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emulator', null, { reload: 'emulator' });
                }, function() {
                    $state.go('emulator');
                });
            }]
        })
        .state('emulator.edit', {
            parent: 'emulator',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emulator/emulator-dialog.html',
                    controller: 'EmulatorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emulator', function(Emulator) {
                            return Emulator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emulator', null, { reload: 'emulator' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emulator.delete', {
            parent: 'emulator',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emulator/emulator-delete-dialog.html',
                    controller: 'EmulatorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Emulator', function(Emulator) {
                            return Emulator.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emulator', null, { reload: 'emulator' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
