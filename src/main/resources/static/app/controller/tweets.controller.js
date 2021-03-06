(function() {
    angular
        .module("Tweets")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(tweetsService) {
        var vm = this;

        vm.noTweets = false;
        vm.pageSize = 5;

        vm.postTweet = postTweet;
        vm.pagingFunction = pagingFunction;
        vm.pagingComments = pagingComments;
        vm.getDate = getDate;
        vm.collapseComments = collapseComments;
        vm.postComment = postComment;
        vm.dislikeTweet = dislikeTweet;
        vm.likeTweet = likeTweet;

        activate();

        function activate() {
            vm.noTweets = false;
            vm.tweet = {};
            vm.comment = {};
            vm.tweets = [];
            vm.page = 0;
            getTweets();
        }

        function collapseComments(tweet) {
            tweet.commentsCollapsed = !tweet.commentsCollapsed;

            if (!tweet.commentsCollapsed)
                if (tweet.page == 0 && tweet.expandedForFirstTime) {
                    getTweetComments(tweet);
                    tweet.expandedForFirstTime = false;
                }
        }

        function pagingComments(tweet) {
            if (tweet.noComments)
                return;

            tweet.page++;
            getTweetComments(tweet);
        }

        function getTweetComments(tweet) {
            tweetsService
                .getTweetComments(tweet.id, tweet.page, vm.pageSize)
                .then(function(data){
                    tweet.comments.push.apply(tweet.comments, data);
                }, function(){
                    tweet.noComments = true;
                });
        }

        function postComment(tweet, addCommentForm) {
            if(addCommentForm.$invalid)
                return;

            tweetsService
                .createComment(tweet.id, tweet.comment)
                .then(function(data){
                    addCommentForm.$setPristine();
                    tweet.comment = {};
                    tweet.comments = [];
                    tweet.page = 0;
                    tweet.commentsCount++;
                    tweet.noComments = false;
                    tweet.expandedForFirstTime = true;

                    if (!tweet.commentsCollapsed) {
                        getTweetComments(tweet);
                        tweet.expandedForFirstTime = false;
                    }
                }, function(data){
                    if(data.status == 400)
                        createNotification(data.data, "danger");
                    else
                        createNotification("Something wrong happened when you tried to add a new comment!", "danger");
                });
        }

        function getDate(dateArray){
            var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4]);
            return moment(date).format('DD-MM-YYYY hh:mm A');
        }

        function pagingFunction() {
            vm.page++;
            getTweets();
        }

        function getTweets() {
            tweetsService
                .getTweets(vm.page, vm.pageSize)
                .then(function(data){
                    data.forEach(function(tweet) {
                        tweet.commentsCollapsed = true;
                        tweet.page = 0;
                        tweet.comments = [];
                        tweet.expandedForFirstTime = true;
                    });
                    vm.tweets.push.apply(vm.tweets, data);
                }, function(){
                    vm.noTweets = true;
                });
        }

        function postTweet(addTweetForm) {
            if(addTweetForm.$invalid)
                return;

            tweetsService
                .createTweet(vm.tweet)
                .then(function(data){
                    activate();
                    addTweetForm.$setPristine();
                }, function(data){
                    if(data.status == 400)
                        createNotification(data.data, "danger");
                    else
                        createNotification("Something wrong happened when you tried to add a new tweet!", "danger");
                });
        }

        function likeTweet(tweet) {
            tweetsService
                .likeTweet(tweet.id)
                .then(function(data){
                    tweet.usersWhoDislikedCount = data.usersWhoDislikedCount;
                    tweet.usersWhoLikedCount = data.usersWhoLikedCount;
                    tweet.liked = data.liked;
                    tweet.disliked = data.disliked;
                });
        }

        function dislikeTweet(tweet) {
            tweetsService.dislikeTweet(tweet.id)
                .then(function(data) {
                    tweet.usersWhoDislikedCount = data.usersWhoDislikedCount;
                    tweet.usersWhoLikedCount = data.usersWhoLikedCount;
                    tweet.liked = data.liked;
                    tweet.disliked = data.disliked;
            })
        }

        function createNotification(message, type) {
            $.notify(message,{
                placement: {
                    from: "bottom",
                    align: "right"
                },
                type: type,
                delay: 1500,
                animate: {
                    enter: 'animated fadeInUp',
                    exit: 'animated fadeOutDown'
                },
                template: '<div data-notify="container" class="col-xs-11 col-sm-2 alert alert-{0}" role="alert">' +
                '<button type="button" aria-hidden="true" class="close" data-notify="dismiss">×</button>' +
                '<span data-notify="icon"></span> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message">{2}</span>' +
                '<div class="progress" data-notify="progressbar">' +
                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                '</div>' +
                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                '</div>'
            });
        }
    }
})();