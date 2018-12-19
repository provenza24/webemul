(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('EmulatorDialogController', EmulatorDialogController);

    EmulatorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Emulator'];

    function EmulatorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Emulator) {
        var vm = this;

        vm.emulator = entity;
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
            if (vm.emulator.id !== null) {
                Emulator.update(vm.emulator, onSaveSuccess, onSaveError);
            } else {
                Emulator.save(vm.emulator, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webApp:emulatorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
