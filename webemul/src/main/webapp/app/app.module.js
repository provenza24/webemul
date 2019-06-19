(function() {
    'use strict';

    angular
        .module('webApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'ngToast',
            'ngTagsInput',
            'pageslide-directive',            
            'ui.bootstrap.contextMenu'
            
            // jhipster-needle-angularjs-add-module JHipster will add new module here
        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();                
    }
})();
