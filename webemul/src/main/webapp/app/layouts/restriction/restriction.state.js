(function() {
    'use strict';

    angular
        .module('webApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	
    	 $stateProvider
         .state('restriction', {
             parent: 'app',
             url: '/restriction',
             data: {
                 //authorities: ['ROLE_USER'],
                 pageTitle: 'Accès interdit'
             },
             views: {
                 'content@': {
                	 templateUrl: 'app/layouts/restriction/restriction.html',
                     controller: 'RestrictionController',
                     controllerAs: 'vm'
                 }
             }
         });    
    }
})();
