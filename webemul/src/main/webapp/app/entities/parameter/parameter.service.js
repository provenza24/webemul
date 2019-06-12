(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('Parameter', Parameter);

    Parameter.$inject = ['$resource'];

    function Parameter ($resource) {
        var resourceUrl =  'api/parameters/:id';

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
