(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ParameterDialogController', ParameterDialogController);

    ParameterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Parameter'];

    function ParameterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Parameter) {
        var vm = this;

        vm.parameter = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.parameter.id !== null) {
                Parameter.update(vm.parameter, onSaveSuccess, onSaveError);
            } else {
                Parameter.save(vm.parameter, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webApp:parameterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
