(function() {
    angular
        .module("Tweets", ["ngRoute", "ngMessages", "ui.bootstrap", "angular-loading-bar", "infinite-scroll"])
        .factory('authHttpResponseInterceptor',['$q', function($q){
            return {
                response: function(response){
                    return response || $q.when(response);
                }
                /*responseError: function(rejection) {
                    if (rejection.status === 401) {
                        window.location = "/#/login";
                    }
                    return $q.reject(rejection);
                }*/
            }
        }])
        .config(function($routeProvider, $httpProvider, cfpLoadingBarProvider){
            $routeProvider
                .when("/tweets", {
                    templateUrl: "app/template/tweets.html",
                    controller: "ListBookDetailsCtrl",
                    controllerAs: "vm"
                })
                .otherwise({redirectTo:"/tweets"});

            cfpLoadingBarProvider.includeSpinner = false;

            $httpProvider.interceptors.push('authHttpResponseInterceptor');
        });

})();