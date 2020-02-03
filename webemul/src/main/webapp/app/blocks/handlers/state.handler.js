(function() {
    'use strict';

    angular
        .module('webApp')
        .factory('stateHandler', stateHandler);

    stateHandler.$inject = ['$rootScope', '$state', '$sessionStorage',  '$window', '$location', 'VERSION', 'AuthService', 'RomPageConfiguration'];

    function stateHandler($rootScope, $state, $sessionStorage,  $window, $location, VERSION, AuthService, RomPageConfiguration) {
        return {
            initialize: initialize,            
        };
                
        function initialize() {
            $rootScope.VERSION = VERSION;

            var stateChangeStart = $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;
                $rootScope.fromState = fromState;

                // Redirect to a state with an external URL (http://stackoverflow.com/a/30221248/1098564)
                if (toState.external) {
                    event.preventDefault();
                    $window.open(toState.url, '_self');
                }
            });

            var stateChangeSuccess = $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
                var titleKey = 'webApp' ;

                // Set the page title key to the one configured in state or use default one
                if (toState.data.pageTitle) {
                    titleKey = toState.data.pageTitle;
                }
                $window.document.title = titleKey;
            });

            $rootScope.$on('$destroy', function () {
                if(angular.isDefined(stateChangeStart) && stateChangeStart !== null){
                    stateChangeStart();
                }
                if(angular.isDefined(stateChangeSuccess) && stateChangeSuccess !== null){
                    stateChangeSuccess();
                }
            });                         
            
            $rootScope.reload = function() {
            	$window.location.href = "/";
            	$window.location.reload();
            }
            
            $rootScope.search = function(searchTerm) {
            	var form = {page: 1, itemsPerPage: 20, queryCount: 0, totalItems: 0, predicate: 'name', reverse:true, display: 'LINE', console:{id:''}, firstLetterRange: '', gameName: searchTerm, genre:{id:''}};
    			RomPageConfiguration.setForm(form);    			
    			$state.go('rom', {}, { reload: true });
            }
        }
    }
})();
