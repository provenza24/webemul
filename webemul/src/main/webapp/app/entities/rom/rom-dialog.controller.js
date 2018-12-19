(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('RomDialogController', RomDialogController);

    RomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rom', 'Console', 'Genre'];

    function RomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rom, Console, Genre) {
        var vm = this;

        vm.rom = entity;
        vm.clear = clear;
        vm.save = save;
        vm.consoles = Console.query();
        vm.genres = Genre.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rom.id !== null) {
                Rom.update(vm.rom, onSaveSuccess, onSaveError);
            } else {
                Rom.save(vm.rom, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webApp:romUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
