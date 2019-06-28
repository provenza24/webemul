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
				var matchingGameId = getMatchingGame(vm.rom.name, vm.games);
				if (matchingGameId!=null) {
					download(matchingGameId);
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
		
		function getMatchingGame(searchedGame, gameList) {
			if (gameList.length==1) {
				return gameList[0].id;
			}
			for (var i=0; i<gameList.length;i++) {
				if (gameList[i].game_title.toUpperCase() == searchedGame.toUpperCase()) {
					return gameList[i].id;
				}
			}
			return null;
		}
			
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
