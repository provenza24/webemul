(function() {
    'use strict';

    angular
        .module('webApp')
        .controller('GenreController', GenreController);

    GenreController.$inject = ['Genre'];

    function GenreController(Genre) {

        var vm = this;

        vm.genres = [];

        loadAll();

        function loadAll() {
            Genre.query(function(result) {
                vm.genres = result;
                vm.searchQuery = null;
            });
        }
    }
})();
