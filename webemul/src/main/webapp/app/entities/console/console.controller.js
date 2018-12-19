(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleController', ConsoleController);

    ConsoleController.$inject = ['Console'];

    function ConsoleController(Console) {

        var vm = this;

        vm.consoles = [];

        loadAll();

        function loadAll() {
            Console.query(function(result) {
                vm.consoles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
