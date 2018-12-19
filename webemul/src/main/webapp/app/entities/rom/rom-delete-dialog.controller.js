(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('RomDeleteController',RomDeleteController);

    RomDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rom'];

    function RomDeleteController($uibModalInstance, entity, Rom) {
        var vm = this;

        vm.rom = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
