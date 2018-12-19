(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('Console', Console);

    Console.$inject = ['$resource'];

    function Console ($resource) {
        var resourceUrl =  'api/consoles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
