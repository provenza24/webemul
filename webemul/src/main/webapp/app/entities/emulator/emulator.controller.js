(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('EmulatorController', EmulatorController);

    EmulatorController.$inject = ['Emulator'];

    function EmulatorController(Emulator) {

        var vm = this;

        vm.emulators = [];

        loadAll();

        function loadAll() {
            Emulator.query(function(result) {
                vm.emulators = result;
                vm.searchQuery = null;
            });
        }
    }
})();
