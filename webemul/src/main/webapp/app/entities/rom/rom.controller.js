(function() {
	'use strict';

	angular.module('webApp').controller('RomController', RomController);

	RomController.$inject = [ '$state','$stateParams', 'DataUtils', 'Console', 'Rom',
			'ParseLinks', 'AlertService', 'paginationConstants', 'RomPageConfiguration' ];

	function RomController($state, $stateParams, DataUtils, Console, Rom, ParseLinks,
			AlertService, paginationConstants, RomPageConfiguration) {

		console.log("rom.controller::inController")
		
		var vm = this;
		
		vm.form = RomPageConfiguration.getForm();		
		if (vm.form==null) {
			vm.form = {page: 1, itemsPerPage: 20, queryCount: 0, totalItems: 0, predicate: '', reverse:false, display: 'LINE', consoleId: '', firstLetterRange: ''};
			RomPageConfiguration.setForm(vm.form);
		} else {
			vm.totalItems = 1000;
		}
				
		vm.loadPage = loadPage;
		vm.loadAll = loadAll;
		vm.itemsPerPage = vm.form.itemsPerPage;
		vm.consoles = Console.query();
		vm.changeConsole = changeConsole;
		vm.loading = true;		
		vm.checked = false;
        vm.size = '100px';
        vm.changeFirstRangeLetter = changeFirstRangeLetter;                
        
        vm.range = function(min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };
        
        vm.toggle = function() {
            vm.checked = !vm.checked
        }

		loadAll();

		function changeConsole(consoleId) {
			vm.form.consoleId = consoleId;
			vm.form.firstLetterRange = '';
			vm.form.page = 1;
			vm.form.sort = '';
			RomPageConfiguration.setForm(vm.form);
			loadAll();
		}
		
		function loadAll() {
			console.log("rom.controller::loadAll")
			vm.loading = true;
			Rom.query({				
				page : vm.form.page - 1,
				size : vm.form.itemsPerPage,
				sort : sort(),
				consoleId : vm.form.consoleId,
				firstLetterRange: vm.form.firstLetterRange
			}, onSuccess, onError);
			function sort() {
				var result = [ vm.form.predicate + ','
						+ (vm.form.reverse ? 'asc' : 'desc') ];
				if (vm.form.predicate !== 'id') {
					result.push('id');
				}
				return result;
			}
			function onSuccess(data, headers) {
				vm.links = ParseLinks.parse(headers('link'));
				vm.totalItems = headers('X-Total-Count');
				vm.queryCount = vm.totalItems;
				vm.roms = data;
				for (var i = 0; i < vm.roms.length; i++) {
					vm.roms[i].img = 'data:' + vm.roms[i].coverContentType
							+ ';base64,' + vm.roms[i].cover;	
					vm.popover = {
				            content: "The lemon is a species of small evergreen tree native to Asia. The tree's ellipsoidal yellow fruit is used for culinary and non-culinary purposes throughout the world, primarily for its juice, which has both culinary and cleaning uses.",
				            templateUrl: "app/entities/rom/popoverImageTemplate.html",
				            title: "Lemon",
				            image: vm.roms[i].img,
				            placement: "bottom-left"
				        };
				}								
				vm.loading = false;
			}
			function onError(error) {
				vm.loading = false;
				AlertService.error(error.data.message, error);
			}
		}

		function loadPage(page) {
						
			console.log("rom.controller::loadPage")				
			RomPageConfiguration.setForm(vm.form);
			vm.loadAll();					
		}

		/*function transition() {
			RomPageConfiguration.setForm(vm.form);
			$state.transitionTo($state.$current, {
				firstLetterRange: vm.form.firstLetterRange,
				consoleId: vm.form.consoleId,
				page : vm.form.page,
				sort : vm.form.predicate + ',' + (vm.form.reverse ? 'asc' : 'desc'),
				search : vm.form.currentSearch
			});
		}*/
		
		function changeFirstRangeLetter(range) {
			vm.form.firstLetterRange = range;
			vm.form.page = 1;
			vm.form.sort = '';
			RomPageConfiguration.setForm(vm.form);
			loadAll();					
		}
				
	}
})();
