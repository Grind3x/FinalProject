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
        <div class="col-sm-2">
            <table class="table table-borderless table-responsive-sm">
                <tbody>
                <c:set var="j" scope="page" value="1"/>
                <c:forEach var="i" begin="1" end="5">
                    <tr>
                        <c:forEach var="k" begin="${j}" end="${j+2}">
                            <td>
                                <c:if test="${question_number ne k}">
                                    <a href="/test?t=${test.getId()}&q=${k}">
                                        <button type="button" class="btn btn-outline-info btn-block">${k}</button>
                                    </a>
                                </c:if>


                                <c:if test="${question_number eq k}">
                                    <a href="/test?t=${test.getId()}&q=${k}">
                                        <button type="button" class="btn btn-primary btn-block">${k}</button>
                                    </a>
                                </c:if>
                            </td>
                        </c:forEach>
                    </tr>
                    <c:set var="j" scope="page" value="${j + 3}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Вопрос ${question_number}.</h4></div>
                <div class="panel-body">
                    <h4>${question.getText()}</h4>
                    <c:if test="${question.isMultivariate()}">
                        <c:forEach items="${question.getOptions()}" var="option">
                            <label><input type="checkbox" name="${question.getId()}"> ${option.getOptionText()}
                            </label><br>
                        </c:forEach>
                    </c:if>
                    <c:if test="${not question.isMultivariate()}">
                        <c:forEach items="${question.getOptions()}" var="option">
                            <label><input type="radio" name="${question.getId()}"> ${option.getOptionText()}</label><br>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <a href="/test?t=${test.getId()}&q=${question_number+1}">
                <button type="button" class="btn pull-right">Следующий вопрос</button>
            </a>
        </div>
    </div>
</div>
<footer class="text-center">2018, <strong>Grind3x</strong></footer>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>


















