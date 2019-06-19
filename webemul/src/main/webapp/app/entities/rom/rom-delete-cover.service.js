(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('RomDeleteCover', RomDeleteCover);

    RomDeleteCover.$inject = ['$resource'];

    function RomDeleteCover ($resource) {
        var resourceUrl =  'api/roms/delete-cover/:id';

        return $resource(resourceUrl, {}, {
        	'delete' : {
            	method: 'DELETE'
            }
        });
    }
})();
