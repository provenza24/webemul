(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$rootScope', '$scope', '$stateParams', '$state'];

    function HomeController ($rootScope, $scope, $stateParams, $state) {
    	    	
    	console.log("HomeController");
    	var vm = this;     	
    	vm.loading = true;        
    	vm.loading = false;
    	
    	vm.data = {};
    	vm.data.cb1 = true;
    }
})();
