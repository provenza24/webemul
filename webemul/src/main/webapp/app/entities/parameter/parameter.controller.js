(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('ParameterController', ParameterController);

    ParameterController.$inject = ['$scope', '$sce', 'Parameter', 'ngToast', 'AlertService'];

    function ParameterController($scope, $sce, Parameter, ngToast, AlertService) {

        var vm = this;

        vm.parameters = [];
        vm.isActive=false;        
        vm.toggle = false;
        vm.changeStatus = changeStatus;
        vm.save = save;
        vm.saveTags=saveTags;
        vm.tagsValues = [];        
        
        loadAll();
        
        vm.startsWith = function(string, prefix){
        	  return string.startsWith(prefix);
        }
        
        function loadAll() {
            Parameter.query(function(result) {
                vm.parameters = result;
                initTagsValues();
                vm.searchQuery = null;
            });
        }
        
        function initTagsValues() {
        	for( var i = 0; i < vm.parameters.length; i++ ) {
        		if (vm.parameters[i].type.indexOf('TAGS')==0) {
        			var value = vm.parameters[i].value;
        			vm.tagsValues[i] = [];
        			var values = value.split(',');        			
        			for( var j = 0; j < values.length; j++ ) {
        				if (value!='') {
        					vm.tagsValues[i].push({text:values[j]});
        				}
        			}        			
        		}
        	}        	
        }
        
        function saveTags(parameter, index) {
        	
        	var isFirst = true;
        	parameter.value='';
        	
        	for( var i = 0; i < vm.tagsValues[index].length; i++ ) {
        		if (isFirst) {
        			parameter.value = vm.tagsValues[index][i].text;
        			isFirst = false;
        		} else {
        			parameter.value = parameter.value + ',' + vm.tagsValues[index][i].text;
        		}
        	}        	
        	Parameter.update(parameter, onSaveSuccess, onSaveError);        	
        }
        
        function changeStatus(parameter) {
        	parameter.value = parameter.value=='OFF' ? 'ON' : 'OFF';
        	Parameter.update(parameter, onSaveSuccess, onSaveError);
        }
        
        function onSaveSuccess (result) {            
            AlertService.showToasts(ngToast);
        }

        function onSaveError (error) {
        	AlertService.error("Erreur lors de la mise à jour du paramètre", error);          
        }
        
        function save(parameter) {        	        	
        	vm.loading = true;
        	Parameter.update(parameter, onSaveSuccess, onSaveError);        	
        }
    }
})();
