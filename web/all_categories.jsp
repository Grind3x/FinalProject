<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Система быстрого тестирования - Главная</title>
    <meta name="generator" content="Bootply"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/WEB-INF/style/bootstrap.min.css">
    <link rel="stylesheet" href="/WEB-INF/style/styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<style>
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
                    <h4>Список доступных тестов:</h4>
                </div>
                <div class="panel-body">
                    <div class="container-fluid">
                        <c:set var="i" value="1"/>
                        <c:set var="rowOpened" value="false"/>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${i%3 ne 0 and !rowOpened}">
                                <div class="row">
                                <c:set var="rowOpened" value="true"/>
                            </c:if>
                            <div class="col-sm-4">
                                <div class="card flex-md-row mb-4 box-shadow h-md-250">
                                    <div class="card-body d-flex flex-column align-items-start">
                                        <h5 class="text-dark">${category.getName()}</h5>
                                        <c:forEach items="${category.getTests()}" var="test">
                                            <a href="/test?t=${test.getId()}">${test.getName()}</a><br>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${i%3 eq 0}">
                                </div><br>
                                <c:set var="rowOpened" value="false"/>
                            </c:if>
                            <c:set var="i" value="${i + 1}"/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="text-center">2018, <strong>Grind3x</strong></footer>
</body>
</html>


















