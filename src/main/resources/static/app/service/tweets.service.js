(function() {
    angular
        .module("Tweets")
        .factory("tweetsService", tweetsService);

    function tweetsService($http) {
        var service = {
            createTweet: createTweet,
            getBooks: getBooks
        };

        return service;

        function createTweet(tweet) {
            return $http.post("/tweets", tweet)
                .then(function (response) {
                    return response.data;
                });
        }

        function getBooks() {
            console.log("service");
            return $http.get("/tweets")
                .then(function (response) {
                    return response.data;
                });
        }

    }
})();