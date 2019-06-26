(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ConsoleDialogController', ConsoleDialogController);

    ConsoleDialogController.$inject = ['$q','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'Console', 'Emulator', 'AlertService'];

    function ConsoleDialogController ($q, $timeout, $scope, $stateParams, $uibModalInstance, Console, Emulator, AlertService) {
        
    	var vm = this;

        vm.clear = clear;
        vm.save = save;
        vm.loading = true;
        
        Console.get({id : $stateParams.id}).$promise.then(function (data) {
        	vm.console = data;        	
            return Emulator.query().$promise;
    	}).then(function (data) {
    		vm.emulators = data;
    		vm.loading = false;
    	}).catch(function(error){        		        		
    		vm.loading = false;
    		AlertService.error("Erreur lors du chargement de la page", error);
    	});
                        
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
            $uibModalInstance.close(result);            
        }

        function onSaveError () {
            vm.isSaving = false;
            AlertService.error("Erreur lors de la sauvegarde !", error);
        }

    }
})();
