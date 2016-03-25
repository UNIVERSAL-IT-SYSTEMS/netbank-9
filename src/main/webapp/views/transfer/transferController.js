'use strict';

angular.module('myApp.transfer', ['ngRoute'])

    .controller('transferController', ['$scope', '$log', '$location', 'AccountService', 'AlertService',
        function ($scope, $log, $location, AccountService, AlertService) {
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
                $log.log('Selected: ' + account);
                $scope.possibleDestinationAccounts = [];
                angular.forEach($scope.accounts, function(item) {
                    if ((!(item.id === account.id)) && (item.currency === account.currency)) {
                        $scope.possibleDestinationAccounts.push(item);
                    }
                });
                $scope.selectedDestination = null;
            };


            $scope.selectDestination = function (account) {
                $scope.selectedDestination = account;
                $log.log('Selected: ' + account);
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
            }

        }]);