(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('Rom', Rom);

    Rom.$inject = ['$resource'];

    function Rom ($resource) {
        var resourceUrl =  'api/roms/:id';

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
