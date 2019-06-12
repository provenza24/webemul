(function() {
	'use strict';

	angular.module('webApp').controller('RomGamesController', RomGamesController);

	RomGamesController.$inject = [ '$uibModalInstance', '$state', '$stateParams', 'Rom', 'TheGamesDbGames', 'TheGamesDbCovers', 'AlertService' ];

	function RomGamesController($uibModalInstance, $state, $stateParams, Rom, TheGamesDbGames, TheGamesDbCovers, AlertService) {

		var vm = this;		
		vm.clear = clear;
		vm.download = download;
		vm.loading = true;
		
		Rom.get({id : $stateParams.id}, function(result) {			
			vm.rom = result;
			TheGamesDbGames.query({name : vm.rom.name, consoleId: vm.rom.consoleId}, function(result) {
				vm.games = result;
				vm.loading = false;
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
			TheGamesDbCovers.get({id: gameId, romId: vm.rom.id}, function(result) {
				vm.loading = false;
				$uibModalInstance.close(result);
			}, function(error) {
				vm.loading = false;
				AlertService.error("Error while downloading cover from thegamesdb.net", error);
			})
		}
	}
})();
