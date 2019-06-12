(function() {
    'use strict';

    var jhiAlert = {
        template: '<div class="alerts" ng-cloak="" role="alert">'+
		'<div uib-alert ng-repeat="alert in $ctrl.alerts" ng-cloak="" ng-show="alert.type==\'danger\'" type="{{alert.type}}" close="alert.close($ctrl.alerts)">{{alert.msg}}'+
		'</div>'+
	'</div>',
        controller: jhiAlertController
    };

    angular
        .module('webApp')
        .component('jhiAlert', jhiAlert);

    jhiAlertController.$inject = ['$scope', '$sce', 'AlertService', 'ngToast'];

    function jhiAlertController($scope, $sce, AlertService, ngToast) {
        var vm = this;
        vm.alerts = AlertService.get();
        /*for (var i = 0; i < vm.alerts.length; i++) {        	
        	if ((!angular.isDefined(vm.alerts[i].displayed) || vm.alerts[i].displayed==false) && vm.alerts[i].type!='alert') {
        		ngToast.create({
    				className: vm.alerts[i].type,
    				content: $sce.getTrustedHtml(vm.alerts[i].msg)
    			});
        		vm.alerts[i].displayed = true;
        	}        	        			
        }*/
        $scope.$on('$destroy', function () {
            vm.alerts = [];
        });
    }
})();
