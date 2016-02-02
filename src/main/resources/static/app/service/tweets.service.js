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

        function getTweets() {
            console.log("service");
            return $http.get("/tweets")
                .then(function (response) {
                    return response.data;
                    //console.log(response.data);
                });
        }

    }
})();