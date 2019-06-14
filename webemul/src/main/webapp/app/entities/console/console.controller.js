(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleController', ConsoleController);

    ConsoleController.$inject = ['Console', 'ConsoleScan'];

    function ConsoleController(Console, ConsoleScan) {

        var vm = this;
        vm.scanRoms = scanRoms;

        vm.consoles = [];

        loadAll();

        function loadAll() {
            Console.query(function(result) {
                vm.consoles = result;
                vm.searchQuery = null;
            });
        }
        
        function scanRoms(consoleId) {
        	ConsoleScan.query({consoleId: consoleId}, function(result) {
        		vm.updatedRoms = result;
        	});
        }
    }
})();
