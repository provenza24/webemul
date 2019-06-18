(function() {
    'use strict';
    angular
        .module('webApp')
        .factory('RomPageConfiguration', RomPageConfiguration);

    RomPageConfiguration.$inject = [];

    function RomPageConfiguration () {
        
    	var form = null;    	
    	
        return {
        	setForm: function(value) {        		
        		form = value;        		
        	},
        	getForm: function() {
        		return form;
        	}
        };
    }
})();
