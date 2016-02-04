$.get( "/principal", function(data) {
    angular.element(document).ready(function() {
        var myApp = angular.module('Tweets');
        myApp.value('principal', data);

        angular.bootstrap(document, ['Tweets']);
    });
});