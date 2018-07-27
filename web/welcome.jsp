<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Bootstrap 3 Admin</title>
    <meta name="generator" content="Bootply"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<style>
    <%@include file="css/bootstrap.min.css" %>
    <%@include file="css/styles.css" %>
    footer {
        position: fixed;
        height: 50px;
        bottom: 0;
        width: 100%;
    }
</style>
<body>
<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Главная</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><p class="navbar-text pull-right">
                    Вы вошли как ${user.getFullName()}
                </p></li>
                <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Выйти</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Система быстрого тестирования.</h4></div>
                <div class="panel-body">
                    <h4>Список доступных тестов:</h4>
                    <c:forEach items="${tests}" var="test">
                        <a href="/test?t=${test.getId()}">${test.getName()}</a><br>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <footer class="text-center">2018, <strong>Grind3x</strong></footer>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>
</div>
</body>
</html>


















