(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('AuthService', AuthService);

    AuthService.$inject = ['$rootScope', '$q', '$http'];

    function AuthService ($rootScope, $q, $http) {    	
    	
        return {
        	resolve : function() {
        	    var deferred = $q.defer();
        	    $http.get('api/user').success(function (user) {
            		$rootScope.user = {firstname:"", lastname:"", authorization:0};
             		$rootScope.user.firstname = user.firstname;
             		$rootScope.user.lastname = user.lastname;
             		$rootScope.user.authorization = user.authorization;
             		$rootScope.user.authorizations = user.authorizations;             		
             		$rootScope.user.authenticated = true;
             		$rootScope.user.agentDTO = user.agentDTO;
             		deferred.resolve(user);
            	   }).error(function(data, status, headers, config) {
        	        deferred.reject("Error: request returned status " + status); 
        	    });
        	    return deferred.promise;
        	}        	        	        	
        };
    }
})();




