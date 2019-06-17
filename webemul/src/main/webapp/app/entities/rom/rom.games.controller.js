(function() {
	'use strict';

	angular.module('webApp').controller('RomGamesController', RomGamesController);

	RomGamesController.$inject = [ '$document', '$uibModalInstance', '$state', '$stateParams', 'Rom', 'TheGamesDbGames', 'TheGamesDbCovers', 'AlertService' ];

	function RomGamesController($document, $uibModalInstance, $state, $stateParams, Rom, TheGamesDbGames, TheGamesDbCovers, AlertService) {

		var vm = this;		
		vm.clear = clear;
		vm.download = download;
		vm.loading = true;		
		
		Rom.get({id : $stateParams.id}, function(result) {			
			vm.rom = result;
			TheGamesDbGames.query({name : vm.rom.name, consoleId: vm.rom.consoleId}, function(result) {
				vm.games = result;
				if (vm.games.length==1) {
					download(vm.games[0].id);
				} else {
					vm.loading = false;
				}
			}, function (error) {
				AlertService.error("Error while retrieving rom information from database", error);
				vm.loading = false;
			})			
		}, function (error) {
			AlertService.error("Error while retrieving data from thegamesdb.net website", error);
			vm.loading = false;
		});
			
		function clear () {
            $uibModalInstance.dismiss('cancel');
        }
		
		function download(gameId) {
			vm.loading = true;
			
			/*var myEl = angular.element(document.querySelector('typewrite'));
			myEl.attr('text',"attr val");*/
			/*var element = $document[0].getElementById('typewrite');
			element.attr('text','Test');*/
			
			TheGamesDbCovers.get({id: gameId, romId: vm.rom.id}, function(result) {
				$uibModalInstance.close(result);
				vm.loading = false;
				vm.downloadingCover = false;
			}, function(error) {
				vm.loading = false;
				vm.downloadingCover = false;
				AlertService.error("Error while downloading cover from thegamesdb.net", error);
			})
		}
	}
})();
