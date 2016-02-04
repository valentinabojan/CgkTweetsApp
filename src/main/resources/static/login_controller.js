(function () {
    angular
        .module("Login")
        .controller("LoginCtrl", LoginCtrl);

    function LoginCtrl($scope, $location) {
        $scope.getLoginFailedStatus = function () {
            return $location.search().error;
        }
    }
})();