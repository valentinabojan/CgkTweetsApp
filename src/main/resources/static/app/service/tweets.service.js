(function() {
    angular
        .module("Tweets")
        .factory("tweetsService", tweetsService);

    function tweetsService($http) {
        var service = {
            createTweet: createTweet,
            getTweets: getTweets
        };

        return service;

        function createTweet(tweet) {
            return $http.post("/tweets", tweet)
                .then(function (response) {
                    return response.data;
                });
        }

        function getTweets(page, size) {
            console.log("service");
            return $http.get("/tweets?page=" + page + "&size=" + size)
                .then(function (response) {
                    return response.data;
                });
        }

    }
})();