(function() {
    'use strict';

    angular
        .module('webApp')
        .factory('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$rootScope', '$q', '$location'];

    function authInterceptor ($rootScope, $q, $location) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError (errorResponse) {
        	switch (errorResponse.status) {
            case 403:            	
            	$location.path('/restriction');            	
                break;           
            }
            return $q.reject(errorResponse);
        }
    }
})();
