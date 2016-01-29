(function() {
    angular
        .module("Tweets")
        .controller("LoginCtrl", LoginCtrl);

    function LoginCtrl(loginService, $location) {
        var vm = this;

        vm.user = {};

        vm.login = login;

        function login() {
            loginService
                .loginUser(vm.user)
                .then(function(data){
                    $location.path("/#/tweets");
                    console.log(data);
                }, function(){
                });
        }
    }
})();