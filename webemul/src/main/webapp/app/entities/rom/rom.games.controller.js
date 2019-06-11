(function() {
	'use strict';

	angular.module('webApp').controller('RomGamesController', RomGamesController);

	RomGamesController.$inject = [ '$uibModalInstance', '$state', '$stateParams', 'Rom', 'TheGamesDbGames', 'TheGamesDbCovers', 'AlertService' ];

	function RomGamesController($uibModalInstance, $state, $stateParams, Rom, TheGamesDbGames, TheGamesDbCovers, AlertService) {

		var vm = this;		
		vm.clear = clear;
		vm.download = download;
		
		Rom.get({id : $stateParams.id}, function(result) {			
			vm.rom = result;
			TheGamesDbGames.query({name : vm.rom.name, consoleId: vm.rom.consoleId}, function(result) {
				vm.games = result;
			}, function (error) {
				AlertService.error("Error while retrieving rom information from database", error);
			})			
		}, function (error) {
			AlertService.error("Error while retrieving data from thegamesdb.net website", error);
		});
			
		function clear () {
            $uibModalInstance.dismiss('cancel');
        }
		
		function download(gameId) {
			
			TheGamesDbCovers.get({id: gameId, romId: vm.rom.id}, function(result) {
				$uibModalInstance.close(result);
			}, function(error) {
				AlertService.error("Error while downloading cover from thegamesdb.net", error);
			})
		}
	}
})();
