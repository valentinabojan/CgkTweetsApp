(function() {
    angular
        .module("Tweets", ["ngRoute", "ngMessages", "ui.bootstrap", "angular-loading-bar"])
        .factory('authHttpResponseInterceptor',['$q', function($q){
            return {
                response: function(response){
                    return response || $q.when(response);
                },
                responseError: function(rejection) {
                    if (rejection.status === 401) {
                        window.location = "/#/login";
                    }
                    return $q.reject(rejection);
                }
            }
        }])
        .config(function($routeProvider, $httpProvider, cfpLoadingBarProvider){
            $routeProvider
                .when("/login", {
                    templateUrl: "app/template/login.html",
                    controller: "LoginCtrl",
                    controllerAs: "vm"
                })
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