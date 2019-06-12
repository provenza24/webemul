(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ParameterDeleteController',ParameterDeleteController);

    ParameterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Parameter'];

    function ParameterDeleteController($uibModalInstance, entity, Parameter) {
        var vm = this;

        vm.parameter = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Parameter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
