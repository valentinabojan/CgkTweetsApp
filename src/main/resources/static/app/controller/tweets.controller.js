(function() {
    angular
        .module("Tweets")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(tweetsService) {
        var vm = this;

        vm.book = {};
        vm.noBook = false;

        activate();

        function activate() {
            tweetsService
                .getBooks()
                .then(function(data){
                    vm.book = data;
                    console.log("bbbbb");
                }, function(){
                    vm.noBook = true;
                });
        }
    }
})();