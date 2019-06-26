(function() {
	'use strict';

	angular.module('webApp').controller('ScanDialogController', ScanDialogController);

	ScanDialogController.$inject = [ '$document', '$uibModalInstance', '$state', '$stateParams', 'ConsoleScan', 'AlertService' ];

	function ScanDialogController($document, $uibModalInstance, $state, $stateParams, ConsoleScan, AlertService) {

		var vm = this;		
		vm.clear = clear;
		vm.loading = true;	
		
		vm.name = $stateParams.name;		
					
		ConsoleScan.query({consoleId: $stateParams.id}, function(result) {
        	vm.updatedRoms = result;
        	vm.loading = false;		
        }, function (error) {
        	vm.loading = false;		
        	alert('error');
        });
        
		function clear () {
            $uibModalInstance.dismiss('cancel');
        }				
	}
})();
