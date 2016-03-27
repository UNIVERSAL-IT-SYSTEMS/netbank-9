'use strict';

angular.module('myApp.transfer', ['ngRoute'])

    .controller('transferController', ['$scope', '$location', 'AccountService', 'AlertService',
        function ($scope, $location, AccountService, AlertService) {
            AccountService.accounts(function (response) {
                $scope.accounts = response.data;
                $scope.possibleDestinationAccounts = response.data;
            }, function (response) {
                AlertService.showDanger('Error while loading accounts!');
            });


            $scope.selectedSource = null;
            $scope.selectedDestination = null;
            $scope.selectedAmount = 0;

            $scope.selectSource = function (account) {
                $scope.selectedSource = account;
                $scope.possibleDestinationAccounts = [];
                angular.forEach($scope.accounts, function(item) {
                    if ((!(item.id === account.id)) && (item.currency === account.currency)) {
                        $scope.possibleDestinationAccounts.push(item);
                    }
                });
                $scope.selectedDestination = null;
                $scope.setSelectedSourceHeader();
            };


            $scope.selectDestination = function (account) {
                $scope.selectedDestination = account;
                $scope.setSelectedDestinationHeader();
            };

            $scope.submitTransaction = function() {
                // TODO check if amount is less then the balance of the account
                AccountService.submitTransaction($scope.selectedSource.id, $scope.selectedDestination.id, $scope.selectedAmount, function() {
                    $location.path("/accounts/" + $scope.selectedSource.id);
                    AlertService.showSuccess("Transaction finished!");
                }, function() {
                    AlertService.showDanger("Transaction failed!");
                });
            };

            $scope.filterSelectedAmount = function() {
                $scope.selectedAmount = $scope.selectedAmount.replace(/[^0-9]/g, '')
            };


            $scope.setSelectedSourceHeader = function () {
                $scope.selectedSourceHeader  = $scope.selectedSource.name + ' (' + $scope.selectedSource.balance + ' ' + $scope.selectedSource.currency + ')';
            };

            $scope.setSelectedDestinationHeader = function () {
                $scope.selectedDestinationHeader  = $scope.selectedDestination.name + ' (' + $scope.selectedDestination.balance + ' ' + $scope.selectedDestination.currency + ')';
            };

        }]);