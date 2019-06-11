(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('TheGamesDbGames', TheGamesDbGames);

    TheGamesDbGames.$inject = ['$resource'];

    function TheGamesDbGames ($resource) {
        var resourceUrl =  'api/thegamesdb/games';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            /*'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }*/
        });
    }
})();
