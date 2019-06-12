(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ParameterDetailController', ParameterDetailController);

    ParameterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Parameter'];

    function ParameterDetailController($scope, $rootScope, $stateParams, previousState, entity, Parameter) {
        var vm = this;

        vm.parameter = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webApp:parameterUpdate', function(event, result) {
            vm.parameter = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
