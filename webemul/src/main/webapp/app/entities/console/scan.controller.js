(function() {
	'use strict';

	angular.module('webApp').controller('ScanDialogController', ScanDialogController);

	ScanDialogController.$inject = [ '$scope', '$document', '$uibModalInstance', '$state', '$stateParams', 'ConsoleScan', '$stomp', 'AlertService' ];

	function ScanDialogController($scope, $document, $uibModalInstance, $state, $stateParams, ConsoleScan, $stomp, AlertService) {

		var vm = this;		
		vm.clear = clear;
		vm.loading = true;	
		
		vm.name = $stateParams.name;		
		
		vm.progress = {message:'', progression: 0};		
  	  
        $stomp.connect('http://localhost:8081/console-update-websocket', {})
        .then(function (frame) {
            var subscription = $stomp.subscribe('/topic/scan-console', 
                function (payload, headers, res) {
            		vm.progress = payload;
                    $scope.$apply(vm.progress);                    
            });        
            ConsoleScan.query({consoleId: $stateParams.id}, function(result) {
            	vm.updatedRoms = result;  
            	vm.loading = false;
            	$stomp.disconnect();
            }, function (error) {
            	$stomp.disconnect();
            	vm.loading = false;		 
            	AlertService.error("Erreur lors de la mise à jour des roms de la console", error);
            });            
        });				
        
		function clear () {
            $uibModalInstance.dismiss('cancel');
        }				
	}
})();
