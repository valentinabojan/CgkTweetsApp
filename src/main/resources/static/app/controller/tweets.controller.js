(function() {
    angular
        .module("Tweets")
        .controller("ListBookDetailsCtrl", ListBookDetailsCtrl);

    function ListBookDetailsCtrl(tweetsService) {
        var vm = this;

        vm.tweets = [];
        vm.noTweets = false;
        vm.page = 0;
        vm.pageSize = 5;

        vm.postTweet = postTweet;
        vm.pagingFunction = pagingFunction;
        vm.getDate = getDate;

        activate();

        function activate() {
            getTweets();
        }

        function getDate(dateArray){
            var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5]);
            return moment(date).format('DD-MM-YYYY hh:mm:ss');
        }

        function pagingFunction() {
            vm.page++;
            getTweets();
        }

        function getTweets() {
            tweetsService
                .getTweets(vm.page, vm.pageSize)
                .then(function(data){
                    vm.tweets.push.apply(vm.tweets, data);
                    if(vm.page==0)console.log(data);
                }, function(){
                    vm.noTweets = true;
                });
        }

        function postTweet(addTweetForm) {
            if(addTweetForm.$invalid)
                return;

            console.log(vm.tweet);

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