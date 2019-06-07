(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('RomDetailController', RomDetailController);

    RomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Rom', 'Console', 'Genre'];

    function RomDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Rom, Console, Genre) {
        var vm = this;

        vm.rom = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('webApp:romUpdate', function(event, result) {
            vm.rom = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
