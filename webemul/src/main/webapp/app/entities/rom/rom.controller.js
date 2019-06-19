(function() {
	'use strict';

	angular.module('webApp').controller('RomController', RomController);

	RomController.$inject = [ '$state','$stateParams', 'DataUtils', 'Console', 'Rom',
			'ParseLinks', 'AlertService', 'paginationConstants', 'RomPageConfiguration', 'RomDeleteCover' ];

	function RomController($state, $stateParams, DataUtils, Console, Rom, ParseLinks,
			AlertService, paginationConstants, RomPageConfiguration, RomDeleteCover) {

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
            vm.checked = !vm.checked;
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
				            content: "",
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
		
		function changeFirstRangeLetter(range) {
			vm.form.firstLetterRange = range;
			vm.form.page = 1;
			vm.form.sort = '';
			RomPageConfiguration.setForm(vm.form);
			loadAll();					
		}
		
		vm.menuOptions = [
		    // NEW IMPLEMENTATION
		    {
		        text: 'Supprimer la jaquette',
		        click: function ($itemScope, $event, modelValue, text, $li) {		        	
		        	RomDeleteCover.delete({id: $itemScope.rom.id}, function(result) {
		        		vm.loadAll();
		        	}, function (error) {
		        		AlertService.error("Erreur lors de la suppression de la jaquette", error);
		        	});		        	
		        }
		    },		    
		    null, // Dividier
		    {
		        text: 'Object-Remove',
		        click: function ($itemScope, $event, modelValue, text, $li) {
		        	console.log("Not implemented");
		        }
		    }
		];
				
	}
})();
