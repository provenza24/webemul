(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('parameter', {
            parent: 'entity',
            url: '/parameter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Parameters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parameter/parameters.html',
                    controller: 'ParameterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('parameter-detail', {
            parent: 'parameter',
            url: '/parameter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Parameter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/parameter/parameter-detail.html',
                    controller: 'ParameterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Parameter', function($stateParams, Parameter) {
                    return Parameter.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'parameter',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('parameter-detail.edit', {
            parent: 'parameter-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter/parameter-dialog.html',
                    controller: 'ParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parameter', function(Parameter) {
                            return Parameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parameter.new', {
            parent: 'parameter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter/parameter-dialog.html',
                    controller: 'ParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                label: null,
                                value: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('parameter', null, { reload: 'parameter' });
                }, function() {
                    $state.go('parameter');
                });
            }]
        })
        .state('parameter.edit', {
            parent: 'parameter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter/parameter-dialog.html',
                    controller: 'ParameterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Parameter', function(Parameter) {
                            return Parameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parameter', null, { reload: 'parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('parameter.delete', {
            parent: 'parameter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/parameter/parameter-delete-dialog.html',
                    controller: 'ParameterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Parameter', function(Parameter) {
                            return Parameter.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('parameter', null, { reload: 'parameter' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
