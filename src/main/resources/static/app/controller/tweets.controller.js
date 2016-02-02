(function() {
    angular
        .module("Tweets")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(tweetsService) {
        var vm = this;

        vm.tweets = [];
        vm.noTweets = false;

        activate();

        function activate() {
            // TODO: Get posts
            tweetsService
                .getTweets()
                .then(function(data){
                    vm.tweets = data;
                    console.log("bbbbb");
                    console.log(data);
                }, function(){
                    vm.noTweets = true;
                });
        }

        vm.postTweet = postTweet;

        function postTweet(addTweetForm) {
            if(addTweetForm.$invalid)
                return;

            tweetsService
                .createTweet(vm.tweet)
                .then(function(data){
                    vm.tweet = {};
                    addTweetForm.$setPristine();
                    vm.tweets.unshift(data);
                }, function(data){
                    if(data.status == 400)
                        createNotification(data.data, "danger");
                    else
                        createNotification("Something wrong happened when you tried to add a new book!", "danger");
                });
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