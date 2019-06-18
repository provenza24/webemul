(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('ParameterSearch', ParameterSearch);

    ParameterSearch.$inject = ['$resource'];

    function ParameterSearch ($resource) {
        var resourceUrl =  'api/parameters/search';

        return $resource(resourceUrl, {}, {
        	'query': { method: 'GET', isArray: true},
            'get': { method: 'GET', isArray: false}        
        });
    }
})();