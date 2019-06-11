(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('TheGamesDbCovers', TheGamesDbCovers);

    TheGamesDbCovers.$inject = ['$resource'];

    function TheGamesDbCovers ($resource) {
        var resourceUrl =  'api/thegamesdb/covers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {method: 'GET'}
        });
    }
})();
