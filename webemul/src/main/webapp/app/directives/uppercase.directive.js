(function() {
    'use strict';

    angular
        .module('webApp')
        .directive('uppercase', uppercase);

    uppercase.$inject = [];

    function uppercase () {
    	return {
            require: 'ngModel',        
            link: function(scope, element, attrs, modelCtrl) {
                modelCtrl.$parsers.push(function(input) {
                    return input ? input.toUpperCase() : "";
                });
                element.css("text-transform","uppercase");
            }
        };
    }
})();