var app = angular.module("loaApp", []);

app.controller("loaCtrl", function($scope, $http) {
    $scope.clickButton = function() {
        $http({
            method: 'GET',
            url: 'http://date.jsontest.com'
        }).then(function successCallback(response) {
            $scope.message = JSON.stringify(response.data);
        }, function errorCallback(response) {
            $scope.message = response.statusText;
        });
    }
});