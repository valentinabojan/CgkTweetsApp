(function() {
    angular
        .module("Tweets")
        .factory("tweetsService", tweetsService);

    function tweetsService($http) {
        var service = {
            createTweet: createTweet,
            getTweets: getTweets,
            getTweetComments: getTweetComments
        };

        return service;

        function createTweet(tweet) {
            return $http.post("/tweets", tweet)
                .then(function (response) {
                    return response.data;
                });
        }

        function getTweets(page, size) {
            return $http.get("/tweets?page=" + page + "&size=" + size)
                .then(function (response) {
                    return response.data;
                });
        }

        function getTweetComments(tweetId, page, size) {
            return $http.get("/tweets/" + tweetId + "/comments?page=" + page + "&size=" + size)
                .then(function (response) {
                    return response.data;
                });
        }

    }
})();