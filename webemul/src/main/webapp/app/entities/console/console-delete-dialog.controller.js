(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleDeleteController',ConsoleDeleteController);

    ConsoleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Console'];

    function ConsoleDeleteController($uibModalInstance, entity, Console) {
        var vm = this;

        vm.console = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Console.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
