(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('EmulatorDeleteController',EmulatorDeleteController);

    EmulatorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Emulator'];

    function EmulatorDeleteController($uibModalInstance, entity, Emulator) {
        var vm = this;

        vm.emulator = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Emulator.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
