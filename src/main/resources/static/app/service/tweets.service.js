(function() {
    angular
        .module("Tweets")
        .factory("tweetsService", tweetsService);

    function tweetsService($http) {
        var service = {
            createTweet: createTweet,
            getTweets: getTweets,
            createComment: createComment,
            getTweetComments: getTweetComments,
            likeTweet: likeTweet,
            dislikeTweet: dislikeTweet
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

        function dislikeTweet(tweetId) {
            return $http.put("/tweets/" + tweetId + "/dislike")
                .then(function (response) {
                    return response.data;
                });
        }

        function createComment(tweetId, comment) {
            return $http.post("/tweets/" + tweetId + "/comments", comment)
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

        function likeTweet(tweetId) {
            return $http.put("/tweets/" + tweetId + "/like")
                .then(function (response) {
                    return response.data;
                });
        }

    }
})();