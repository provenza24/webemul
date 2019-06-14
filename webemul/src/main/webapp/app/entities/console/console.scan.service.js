(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('ConsoleScan', ConsoleScan);

    ConsoleScan.$inject = ['$resource'];

    function ConsoleScan ($resource) {
        var resourceUrl =  'api/scan';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}            
        });
    }
})();
