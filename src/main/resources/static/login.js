(function() {
    console.log("login.js");
    angular
        .module("Login", [])
        .config(function($locationProvider){
            $locationProvider.html5Mode({
                requireBase : false
            })
        });
})();