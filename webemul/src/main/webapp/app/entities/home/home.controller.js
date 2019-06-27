(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', '$stateParams', '$state', '$stomp', 'ConsoleScan'];

    function HomeController ($rootScope, $scope, $stateParams, $state, $stomp, ConsoleScan) {
    	    	
    	var vm = this;     	
    	
    	vm.loading = false;
    	
    }
})();
