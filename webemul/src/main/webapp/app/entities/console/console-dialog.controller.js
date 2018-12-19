(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleDialogController', ConsoleDialogController);

    ConsoleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Console', 'Emulator', 'Rom'];

    function ConsoleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Console, Emulator, Rom) {
        var vm = this;

        vm.console = entity;
        vm.clear = clear;
        vm.save = save;
        /*vm.defaultemulators = Emulator.query({filter: 'console-is-null'});
        $q.all([vm.console.$promise, vm.defaultemulators.$promise]).then(function() {
            if (!vm.console.defaultEmulatorId) {
                return $q.reject();
            }
            return Emulator.get({id : vm.console.defaultEmulatorId}).$promise;
        }).then(function(defaultEmulator) {
            vm.defaultemulators.push(defaultEmulator);
        });*/
        $q.all([vm.console.$promise]).then(function() {
            if (!vm.console.defaultEmulatorId) {
                return $q.reject();
            }
            return Emulator.get({id : vm.console.defaultEmulatorId}).$promise;
        });
        vm.roms = Rom.query();
        vm.emulators = Emulator.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.console.id !== null) {
                Console.update(vm.console, onSaveSuccess, onSaveError);
            } else {
                Console.save(vm.console, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webApp:consoleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
