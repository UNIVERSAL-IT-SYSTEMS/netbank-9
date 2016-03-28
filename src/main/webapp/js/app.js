'use strict';

angular.module('myApp', [
    'ngRoute',
    'myApp.accountBalances',
    'myApp.accountHistory',
    'myApp.transfer'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/accountBalances', {
            templateUrl: 'views/accountBalances/accountBalances.html',
            controller: 'accountBalancesController'
        }).when('/accounts/:id', {
            templateUrl: 'views/accountHistory/accountHistory.html',
            controller: 'accountHistoryController'
        }).when('/transfer', {
            templateUrl: 'views/transfer/transfer.html',
            controller: 'transferController'
        }).otherwise({redirectTo: '/accountBalances'});
    }])
    .service('AccountService', function ($http) {
        this.accounts = function (successHandler, errorHandler) {
            $http.get('backend/accounts').then(successHandler, errorHandler);
        };
        this.accountHistory = function (accountId, successHandler, errorHandler) {
            $http.get('backend/accounts/' + accountId + '/transactions').then(successHandler, errorHandler);
        };
        this.submitTransaction = function (sourceAccountId, destinationAccountId, amount, successHandler, errorHadler) {
            var data = {
                sourceAccountId: sourceAccountId,
                destinationAccountId: destinationAccountId,
                amount: amount
            };
            var config = {xsrfHeaderName: 'X-CSRF-TOKEN', xsrfCookieName: 'XSRF-TOKEN'};
            $http.post('backend/accounts/' + sourceAccountId + '/transactions', data, config).then(successHandler, errorHadler);
        };
    }).controller('navBarController', ['$scope', '$location', '$window', 'AccountService', 'AlertService', 'UserService',
        function ($scope, $location, $window, AccountService, AlertService, UserService) {
            AccountService.accounts(function (response) {
                $scope.accounts = response.data;
            }, function () {
                AlertService.showDanger('Error while loading accounts!');
            });
            UserService.getCurrentUserId(function (response) {
                $scope.currentUserId = response.data.id;
            }, function () {
                AlertService.showDanger('Error while loading the users!');
            });
            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };
            $scope.logoutWithConfirmation = function () {
                var reallyLogOut = confirm("Are you sure?");
                if (reallyLogOut === true) {
                    $window.location.href = "logout";
                }
            };
        }])
    .service('AlertService', function ($rootScope) {
        $rootScope.showAlert = false;
        $rootScope.alert = {type: null, message: null};
        this.showAlert = function (type, message) {
            $rootScope.alert.type = type;
            $rootScope.alert.message = message;
            $rootScope.showAlert = true;
        };
        this.showDanger = function (message) {
            this.showAlert('alert-danger', message);
        };
        this.showSuccess = function (message) {
            this.showAlert('alert-success', message);
        };
    })
    .service('UserService', function ($http) {
        this.getCurrentUserId = function (successHandler, errorHandler) {
            $http.get('backend/users/me').then(successHandler, errorHandler);
        };
    });

