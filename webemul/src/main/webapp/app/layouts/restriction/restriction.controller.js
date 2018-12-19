(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('RestrictionController', RestrictionController);

    RestrictionController.$inject = ['$scope', 'AutoRefresh'];

    function RestrictionController ($scope, AutoRefresh) {    	    	    	
    	
    	// Arrêter l'autorefresh lancé pour la page des incidents journaliers
    	AutoRefresh.stop();             	
    }
})();
