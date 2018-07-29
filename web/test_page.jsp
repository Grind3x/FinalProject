<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Тест: ${test.getName()}, вопрос ${question_number}</title>
    <meta name="generator" content="Bootply"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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
    <form action="/answer" method="post">
        <div class="row">
            <div class="col-sm-2">
                <table class="table table-borderless table-responsive-sm">
                    <tbody>

                    <c:set var="j" scope="page" value="1"/>
                    <c:forEach var="i" begin="1"
                               end="${(questions.size() % 3 eq 0 ? questions.size() / 3 : questions.size() / 3 + 1)}">
                        <tr>
                            <c:forEach var="k" begin="${j}" end="${j+2}">
                                <c:if test="${k le questions.size()}">
                                    <td>
                                        <c:if test="${question_number ne k}">
                                            <c:if test="${not empty answers.get(questions.get(k-1))}">
                                                <button type="submit" formaction="/test?t=${test.getId()}&q=${k}"
                                                        class="btn btn-primary btn-block">${k}</button>
                                            </c:if>
                                            <c:if test="${empty answers.get(questions.get(k-1))}">
                                                <button type="submit" formaction="/test?t=${test.getId()}&q=${k}"
                                                        class="btn btn-outline-info btn-block">${k}</button>
                                            </c:if>

                                        </c:if>
                                        <c:if test="${question_number eq k}">
                                            <a href="/test?t=${test.getId()}&q=${k}">
                                                <button type="button" class="btn btn-success btn-block">${k}</button>
                                            </a>
                                        </c:if>

                                    </td>
                                </c:if>

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
                        <input type="hidden" name="test" value="${test.getId()}">
                        <input type="hidden" name="question" value="${question_number}">
                        <h4>Вопрос ${question_number}.</h4></div>
                    <div class="panel-body">
                        <h4><c:out value="${question.getText()}"/></h4>
                        <c:if test="${question.isMultivariate()}">
                            <c:forEach items="${question.getOptions()}" var="option">
                                <label><input type="checkbox" name="option"
                                              value="${option.getId()}"
                                              <c:if test="${answers.get(questions.get(question_number - 1)).contains(option)}">checked</c:if>
                                >&nbsp;<c:out value="${option.getOptionText()}"/></label><br>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not question.isMultivariate()}">
                            <c:forEach items="${question.getOptions()}" var="option">
                                <label><input type="radio" name="option"
                                              value="${option.getId()}"
                                              <c:if test="${answers.get(questions.get(question_number - 1)).contains(option)}">checked</c:if>
                                >&nbsp;<c:out value="${option.getOptionText()}"/></label><br>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <c:if test="${question_number ne questions.size()}">
                <button type="submit" class="btn pull-right">Следующий вопрос</button>
                </c:if>

                <c:if test="${question_number eq questions.size()}">
                <button type="submit" class="btn pull-right">Закончить тестирование</button>
                </c:if>
    </form>
</div>
<footer class="text-center">2018, <strong>Grind3x</strong></footer>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>


















