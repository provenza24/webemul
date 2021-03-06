(function() {
	'use strict';

	angular.module('webApp').controller('RomController', RomController);

	RomController.$inject = [ '$rootScope', '$state','$stateParams', 'DataUtils', 'Console', 'Rom', 'Genre',
			'ParseLinks', 'AlertService', 'paginationConstants', 'RomPageConfiguration', 'RomDeleteCover' ];

	function RomController($rootScope, $state, $stateParams, DataUtils, Console, Rom, Genre, ParseLinks,
			AlertService, paginationConstants, RomPageConfiguration, RomDeleteCover) {

		var vm = this;
		
		vm.selected = 'other';
		
		// Method called to search Roms
		vm.search = search;
		// Method called when page is changed
		vm.loadPage = loadPage;		
		// Method called to display all Roms (reset all forms)
		vm.resetPage = resetPage;
		// Method called to reset form
		vm.resetForm = resetForm;
		// Method called to display roms for a selected console
		vm.changeConsole = changeConsole;
		// Method called to display roms which name start with a particular letter
		vm.changeFirstRangeLetter = changeFirstRangeLetter; 
		// Method called to download rom cover
		vm.downloadCover = downloadCover;
	    // Method called to make a detailled search   		
		vm.detailedSearch = detailedSearch;
		
		vm.searchForm = {page: 1, itemsPerPage: 10, queryCount: 0, totalItems: 0, predicate: 'name', reverse:true, console: {id:'', name:''}, gameName: '', genre: {id:'', name:''}};
		vm.eraseSearchForm = eraseSearchForm;
		
		function eraseSearchForm() {
			vm.searchForm = {page: 1, itemsPerPage: 10, queryCount: 0, totalItems: 0, predicate: 'name', reverse:true, console: {id:'', name:''}, gameName: '', genre: {id:'', name:''}};
		}
		
		function downloadCover(romId) {
			RomPageConfiguration.setForm(vm.form);
			$state.go('rom-games', {id:romId});
		}
		
		vm.form = RomPageConfiguration.getForm();		
		if (vm.form==null) {
			resetForm();
			vm.form.display = 'LINE';
		} else {
			vm.totalItems = 1000;
		}		
		
		vm.itemsPerPage = vm.form.itemsPerPage;
		
		vm.consoles = Console.query();
		vm.genres = Genre.query();
		
		vm.loading = true;		
		vm.checked = false;        
        
        function resetForm() {
        	var currentDisplay = 'LINE';
        	if (vm.form!=null) {
        		currentDisplay = vm.form.display;
        	}
        	vm.form = {page: 1, itemsPerPage: 10, queryCount: 0, totalItems: 0, predicate: 'name', reverse:true, display: currentDisplay, console: {id:'', name:''}, firstLetterRange: '', gameName: '', genre: {id:'', name:''}};
			RomPageConfiguration.setForm(vm.form);
        }
        
        function resetPage() {
        	resetForm();
			$rootScope.searchTerm = '';
			search();
        }
        
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

		search();

		function changeConsole(consoleId) {		
			vm.eraseSearchForm();
			vm.form.page = 1;
			vm.form.itemsPerPage = 10;
			vm.form.queryCount = 0;
			vm.form.totalItems = 0;
			vm.form.predicate = 'name';
			vm.form.reverse = 'true';
			vm.form.firstLetterRange = '';
			vm.form.console.id = consoleId;			
			//$rootScope.searchTerm = '';
			RomPageConfiguration.setForm(vm.form);
			search();
		}
			
		function detailedSearch() {
			$rootScope.searchTerm = '';
			resetForm();
			if (vm.checked) {
				vm.checked = !vm.checked;
			}
			vm.loading = true;
			Rom.query({				
				page : vm.searchForm.page - 1,
				size : vm.searchForm.itemsPerPage,
				sort : sort(),				
				romSearch: {consoleId : vm.searchForm.console.id, gameName: vm.searchForm.gameName, genreId: vm.searchForm.genre.id}
			}, onSuccess, onError);
			function sort() {
				var result = [ vm.searchForm.predicate + ','
						+ (vm.searchForm.reverse ? 'asc' : 'desc') ];
				if (vm.searchForm.predicate !== 'id') {
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
		
		function search() {
			if (vm.checked) {
				vm.checked = !vm.checked;
			}
			vm.loading = true;
			Rom.query({				
				page : vm.form.page - 1,
				size : vm.form.itemsPerPage,
				sort : sort(),				
				romSearch: {consoleId : vm.form.console.id, gameName: vm.form.gameName, genreId: vm.form.genre.id, firstLetterRange: vm.form.firstLetterRange}
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
			RomPageConfiguration.setForm(vm.form);
			vm.search();							
		}
		
		vm.loadSearchPage = loadSearchPage;
		
		function loadSearchPage(page) {
			vm.detailedSearch();
		}
		
		function changeFirstRangeLetter(range) {
			vm.eraseSearchForm();
			vm.form.gameName = '';
			vm.form.firstLetterRange = range;
			vm.form.page = 1;
			vm.form.sort = '';
			RomPageConfiguration.setForm(vm.form);
			$rootScope.searchTerm = '';			
			search();					
		}
		
		vm.isEmptySearchForm = isEmptySearchForm;
		
		function isEmptySearchForm() {
			return vm.searchForm.console.id=='' && vm.searchForm.gameName=='' && vm.searchForm.genre.id=='';			
		}
		
		vm.menuOptions = [
		    // NEW IMPLEMENTATION
		    {
		        text: 'Supprimer la jaquette',
		        click: function ($itemScope, $event, modelValue, text, $li) {		        	
		        	RomDeleteCover.delete({id: $itemScope.rom.id}, function(result) {
		        		vm.search();
		        	}, function (error) {
		        		AlertService.error("Erreur lors de la suppression de la jaquette", error);
		        	});		        	
		        }
		    }	    
		    ///null, // Dividier
		    /*{
		        text: 'Object-Remove',
		        click: function ($itemScope, $event, modelValue, text, $li) {
		        	console.log("Not implemented");
		        }
		    }*/
		];
				
	}
})();
