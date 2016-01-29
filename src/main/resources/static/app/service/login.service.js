(function() {
    angular
        .module("Tweets")
        .factory("loginService", loginService);

    function loginService($http) {
        var service = {
            loginUser: loginUser
        };

        return service;

        function loginUser(user) {
            return $http.post("/login", user)
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();