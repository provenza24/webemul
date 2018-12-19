(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('EmulatorDetailController', EmulatorDetailController);

    EmulatorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Emulator'];

    function EmulatorDetailController($scope, $rootScope, $stateParams, previousState, entity, Emulator) {
        var vm = this;

        vm.emulator = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webApp:emulatorUpdate', function(event, result) {
            vm.emulator = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
