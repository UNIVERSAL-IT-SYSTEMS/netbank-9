'use strict';

angular.module('myApp.accountBalances', ['ngRoute'])

    .controller('accountBalancesController', ['$scope', '$location', 'AccountService', 'AlertService',
        function ($scope, $location, AccountService, AlertService) {
            AccountService.accounts(function (response) {
                $scope.accounts = response.data;
            }, function () {
                AlertService.showDanger('Error while loading accounts!');
            });

            $scope.openAccount = function (account) {
                $location.path('/accounts/' + account.id);
            };
        }]);