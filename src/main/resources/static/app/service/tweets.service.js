(function() {
    angular
        .module("Tweets")
        .factory("tweetsService", tweetsService);

    function tweetsService($http) {
        var service = {
            getBooks: getBooks
        };

        return service;

        function getBooks() {
            console.log("service");
            return $http.get("/tweets")
                .then(function (response) {
                    return response.data;
                });
        }

    }
})();