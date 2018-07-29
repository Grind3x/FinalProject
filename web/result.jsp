<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Система быстрого тестирования - результаты</title>
    <meta name="generator" content="Bootply"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<style>
    <%@include file="WEB-INF/style/bootstrap.min.css" %>
    <%@include file="WEB-INF/style/styles.css" %>
    footer {
        /*position: fixed;*/
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

<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h2>Результаты теста:</h2>
        <c:if test="${correct ge max_questions*0.75}">
            <p>Тест пройден. Правильных ответов ${correct}/${max_questions}. <br>
                Результаты будут отправлены Вам электронной почтой.</p>
        </c:if>
        <c:if test="${correct lt max_questions*0.75}">
            <p>Тест не пройден. Правильных ответов ${correct}/${max_questions}. <br>
                Результаты будут отправлены Вам электронной почтой.</p>
        </c:if>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <%--<c:set var="questions" scope="page" value="${test.getQuestions()}"/>--%>
            <c:set var="q" scope="page" value="1"/>
            <c:forEach items="${questions}" var="question">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>${q}. <c:out value="${question.getText()}"/></h4>
                    </div>
                    <div class="panel-body">

                        <c:forEach items="${question.getOptions()}" var="option">

                            <c:if test="${not empty answers.get(question)}">
                                <c:if test="${answers.get(question).contains(option) and option.isCorrect()}">
                                    <p class="text-success">&bull;&nbsp;<strong><c:out
                                            value="${option.getOptionText()}"/>&nbsp;<span
                                            class="glyphicon glyphicon-ok" style="color:green"></span></strong></p>
                                </c:if>

                                <c:if test="${answers.get(question).contains(option) and not option.isCorrect()}">
                                    <p>&bull;&nbsp;<c:out value="${option.getOptionText()}"/>&nbsp;<span
                                            class="glyphicon glyphicon-remove" style="color:red"></span></p>
                                </c:if>

                                <c:if test="${not answers.get(question).contains(option) and option.isCorrect()}">
                                    <p class="text-success">&bull;&nbsp;<strong><c:out
                                            value="${option.getOptionText()}"/></strong>
                                    </p>
                                </c:if>
                                <c:if test="${not answers.get(question).contains(option) and not option.isCorrect()}">
                                    <p>&bull;&nbsp;<c:out value="${option.getOptionText()}"/><br></p>
                                </c:if>
                            </c:if>

                            <c:if test="${empty answers.get(question)}">
                                <c:if test="${option.isCorrect()}">
                                    <p class="text-success">&bull;&nbsp;<strong><c:out
                                            value="${option.getOptionText()}"/></strong>
                                    </p>
                                </c:if>
                                <c:if test="${not option.isCorrect()}">
                                    <p>&bull;&nbsp;<c:out value="${option.getOptionText()}"/></p>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <c:set var="q" scope="page" value="${q + 1}"/>
            </c:forEach>
            <br>
        </div>
    </div>
</div>
<footer class="text-center">2018, <strong>Grind3x</strong></footer>
</body>
</html>


















