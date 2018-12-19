(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div style="padding:5px;background-color:#337ab7; color:white" class="info">' +
        'Enregistrements [{{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
        '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}}] ' +
        'sur {{$ctrl.queryCount}}'
        +'</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('webApp')
        .component('jhiItemCount', jhiItemCount);
})();
