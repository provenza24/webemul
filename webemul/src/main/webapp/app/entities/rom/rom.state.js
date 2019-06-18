(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rom', {
            parent: 'entity',
            url: '/rom?page&sort&search&consoleId&firstLetterRange',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Roms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rom/roms.html',
                    controller: 'RomController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            }
        })
        
        .state('rom-games', {
            parent: 'rom',
            url: '/{id}/thegamesdb/games?name',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TheGamesDb games'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                	templateUrl: 'app/entities/rom/rom-games.html',
                    controller: 'RomGamesController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md'                    
                }).result.then(function() {
                    $state.go('^', {consoleId: (angular.isDefined($stateParams.consoleId) ? $stateParams.consoleId : '')}, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })                       
        .state('rom-detail', {
            parent: 'rom',
            url: '/rom/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rom'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rom/rom-detail.html',
                    controller: 'RomDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rom', function($stateParams, Rom) {
                    return Rom.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rom',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rom-detail.edit', {
            parent: 'rom-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rom/rom-dialog.html',
                    controller: 'RomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rom', function(Rom) {
                            return Rom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rom.new', {
            parent: 'rom',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rom/rom-dialog.html',
                    controller: 'RomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                pathFile: null,
                                extension: null,
                                pathCover: null,
                                cover: null,
                                coverContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rom', null, { reload: 'rom' });
                }, function() {
                    $state.go('rom');
                });
            }]
        })
        .state('rom.edit', {
            parent: 'rom',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rom/rom-dialog.html',
                    controller: 'RomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rom', function(Rom) {
                            return Rom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rom', null, { reload: 'rom' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rom.delete', {
            parent: 'rom',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rom/rom-delete-dialog.html',
                    controller: 'RomDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rom', function(Rom) {
                            return Rom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rom', null, { reload: 'rom' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
