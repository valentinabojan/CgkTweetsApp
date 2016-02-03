(function () {
    angular
        .module("Login")
        .controller("LoginCtrl", LoginCtrl);

    function LoginCtrl($scope, $location, $timeout) {

        console.log("ggggggggggg");

        $scope.hideTimeout = 5000;
        $scope.urlChangeTimeout = $scope.hideTimeout + 2100;

        $scope.hideStuff = function () {
            $scope.startFade = true;
        };

        $scope.loginFailed = function () {
            $timeout($scope.hideStuff, $scope.hideTimeout);
            $timeout($scope.setUrl, $scope.urlChangeTimeout);
            return $scope.getloginFailedStatus();
        }

        $scope.logoutSuccess = function () {
            $timeout($scope.hideStuff, $scope.hideTimeout);
            $timeout($scope.setUrl, $scope.urlChangeTimeout);
            return $scope.getLogoutStatus();
        }

        $scope.setUrl = function () {
            $location.url($location.path())
        }

        $scope.getLogoutStatus = function () {
            return ($location.search()).logout;
        }

        $scope.getloginFailedStatus = function () {
            return ($location.search()).error;
        }
    }
})();