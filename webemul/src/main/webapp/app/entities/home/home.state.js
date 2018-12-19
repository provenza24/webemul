(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	
    	 $stateProvider
         .state('home', {
             parent: 'entity',
             url: '/home',
             data: {
                 //authorities: ['ROLE_USER'],
                 pageTitle: 'Acceuil'
             },
             views: {
                 'content@': {
                	 templateUrl: 'app/entities/home/home.html',
                     controller: 'HomeController',
                     controllerAs: 'vm'
                 }
             }         
         })
    	                	 
         ;    
    }
})();
