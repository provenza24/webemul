(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('RomController', RomController);

    RomController.$inject = ['$state', 'Rom','Console', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', '$sce'];

    function RomController($state, Rom, Console, ParseLinks, AlertService, paginationConstants, pagingParams, $sce) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.consoles = Console.query();
        vm.getTrustedResourceUrl = getTrustedResourceUrl;

        loadAll();

        function loadAll () {
            Rom.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
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
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
        
        function getTrustedResourceUrl (url){
        	//alert(url);
        	//return $sce.getTrustedResourceUrl(url)
        	var test = $sce.getTrustedResourceUrl('f:/NES_1.png');
        	return test;
        };
    }
})();