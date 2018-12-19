(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleDetailController', ConsoleDetailController);

    ConsoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Console', 'Emulator', 'Rom'];

    function ConsoleDetailController($scope, $rootScope, $stateParams, previousState, entity, Console, Emulator, Rom) {
        var vm = this;

        vm.console = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webApp:consoleUpdate', function(event, result) {
            vm.console = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
