(function() {
    'use strict';

    angular
        .module('webApp')
        .directive('capitalize', capitalize);

    capitalize.$inject = [];

    function capitalize () {
    	return {
            require: 'ngModel',        
            link: function(scope, element, attrs, modelCtrl) {
                modelCtrl.$parsers.push(function(input) {
                    return input ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : "";
                });
                element.css("text-transform","capitalize");
            }
        };
    }
})();