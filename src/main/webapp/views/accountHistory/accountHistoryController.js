'use strict';

angular.module('myApp.accountHistory', ['ngRoute'])

    .controller('accountHistoryController', ['$scope', '$routeParams', 'AccountService', 'AlertService',
        function ($scope, $routeParams, AccountService, AlertService) {
            AccountService.accountHistory(
                $routeParams.id,
                function (response) {
                    $scope.accountId = $routeParams.id;
                    $scope.transactions = response.data;
                }, function (response) {
                    AlertService.showDanger('Error while loading accounts');
                });

            $scope.getGlyphType = function(sourceId) {
                return 'glyphicon ' + ($scope.accountId == sourceId ? 'glyphicon-minus' :'glyphicon-plus');
            }
        }]);