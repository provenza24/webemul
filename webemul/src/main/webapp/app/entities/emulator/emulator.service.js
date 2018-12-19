(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('Emulator', Emulator);

    Emulator.$inject = ['$resource'];

    function Emulator ($resource) {
        var resourceUrl =  'api/emulators/:id';

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
