(function() {
    angular
        .module("Tweets")
        .controller("HeaderController", HeaderController);

    function HeaderController(principal) {
        var vm = this;

        vm.principal = principal;
    }
})();