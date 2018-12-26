(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$compileProvider'];

    function stateConfig($stateProvider, $compileProvider) {
    	//$compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|tel|f):/);
        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            }, /*resolve: {            	
         	 	'AuthServiceData':function(AuthService) {            		 
           		 return AuthService.promise;
           	 }, */           	                   
        });
    }

    
    
})();
