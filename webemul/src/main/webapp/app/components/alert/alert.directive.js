(function() {
    'use strict';

    var jhiAlert = {
        template: /*'<div class="alerts" ng-cloak="" role="alert">' +
                        '<div ng-repeat="alert in $ctrl.alerts" ng-class="[alert.position, {\'toast\': alert.toast}]">' +
                            '<uib-alert ng-cloak="" type="{{alert.type}}" close="alert.close($ctrl.alerts)"><pre ng-bind-html="alert.msg"></pre></uib-alert>' +
                        '</div>' +
                  '</div>',*/
        		'<div class="alerts" ng-cloak="" role="alert">'+
        			'<div uib-alert ng-repeat="alert in $ctrl.alerts" ng-cloak="" type="{{alert.type}}" close="alert.close($ctrl.alerts)">{{alert.msg}}'+
        			'</div>'+
        		'</div>',
        controller: jhiAlertController
    };

    angular
        .module('webApp')
        .component('jhiAlert', jhiAlert);

    jhiAlertController.$inject = ['$scope', 'AlertService'];

    function jhiAlertController($scope, AlertService) {
        var vm = this;

        vm.alerts = AlertService.get();
        $scope.$on('$destroy', function () {
            vm.alerts = [];
        });
    }
})();
