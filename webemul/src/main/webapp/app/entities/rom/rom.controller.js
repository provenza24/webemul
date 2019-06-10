(function() {
	'use strict';

	angular.module('webApp').controller('RomController', RomController);

	RomController.$inject = [ '$state', 'DataUtils', 'Console', 'Rom',
			'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams' ];

	function RomController($state, DataUtils, Console, Rom, ParseLinks,
			AlertService, paginationConstants, pagingParams) {

		var vm = this;

		vm.loadPage = loadPage;
		vm.predicate = pagingParams.predicate;
		vm.reverse = pagingParams.ascending;
		vm.transition = transition;
		vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.openFile = DataUtils.openFile;
		vm.byteSize = DataUtils.byteSize;
		vm.consoles = Console.query();
		vm.consoleId = angular.isDefined(pagingParams.consoleId) ? pagingParams.consoleId : '';
		vm.test = test;
		vm.loading = true;

		loadAll();

		function test(consoleId) {
			vm.consoleId = consoleId;
			loadAll();
		}
		
		function loadAll() {
			vm.loading = true;
			Rom.query({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort(),
				consoleId : vm.consoleId
			}, onSuccess, onError);
			function sort() {
				var result = [ vm.predicate + ','
						+ (vm.reverse ? 'asc' : 'desc') ];
				if (vm.predicate !== 'id') {
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
				vm.page = pagingParams.page;
				vm.loading = false;
			}
			function onError(error) {
				vm.loading = false;
				AlertService.error(error.data.message);
			}
		}

		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}

		function transition() {
			$state.transitionTo($state.$current, {
				'consoleId': pagingParams.consoleId,
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});
		}
		
		

		
	}
})();
