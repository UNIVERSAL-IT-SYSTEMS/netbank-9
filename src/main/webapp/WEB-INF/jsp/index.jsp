<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <!-- Latest compiled and minified CSS -->

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

    <%--<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>--%>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular-route.min.js"></script>
    <script src="js/app.js"></script>
    <script src="views/accountBalances/accountBalancesController.js"></script>
    <script src="views/accountHistory/accountHistoryController.js"></script>
    <script src="views/transfer/transferController.js"></script>
    <link href="css/main.css" rel="stylesheet">
    <%--<base href="/">--%>
</head>
<body ng-app="myApp">
<div>
    <div class="container">
        <div>
            <div class="alert {{alert.type || 'alert-success'}}" role="alert" ng-show="showAlert">
                <button type="button" class="close" aria-label="Close" ng-click="showAlert=false">
                    <span aria-hidden="true">&times;</span>
                </button>{{alert.message || ''}}
            </div>
        </div>
        <nav class="navbar navbar-default" ng-controller="navBarController">
            <div class="container-fluid">
                <div class="navbar-header">

                    <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-credit-card"></span>  MyNetBank</a>
                </div>
                <ul class="nav navbar-nav">
                    <%--<li class="active"><a href="#">Home</a></li>--%>
                    <li ng-class="{ active: isActive('/accountBalances')}"><a href="#/accountBalances">
                        <span class="glyphicon glyphicon-stats"></span> Account balances</a></li>
                    <li ng-class="{ active: isActive('/transfer')}"><a href="#/transfer"><span class="glyphicon glyphicon-transfer"></span> Transfer</a></li>
                    <%--<li><a href="#">Account history</a></li>--%>
                    <li class="dropdown" ng-class="{ active: isActive('/accounts')}">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-time"> History <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li ng-repeat="account in accounts">
                                <a href="#/accounts/{{account.id}}">({{account.currency}}) {{account.name}}</a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> {{currentUserId}} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="" ng-click="logoutWithConfirmation()">Log out</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <div ng-view></div>
    </div>



</div>

</body>

</html>