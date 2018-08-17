<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty lang ? lang : sessionScope.language}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title><fmt:message key="admin.remove-test.title"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <meta name="generator" content="Bootply"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

    <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
          integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<style>
    <%@include file="/WEB-INF/style/bootstrap.min.css" %>
    <%@include file="/WEB-INF/style/styles.css" %>
    footer {
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
                <li><a href="/tests"><i class="fas fa-book"></i> Тесты</a></li>

                <li><a href="/logout"><i class="fas fa-sign-out-alt"></i> Выйти</a></li>
            </ul>
        </div>
    </div>
</div>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3">
            <strong><i class="fas fa-link"></i> <fmt:message key="admin.menu.assessments"/> </strong>
            <hr>
            <ul class="nav nav-pills nav-stacked">
                <li class="nav-header"></li>
                <li><a href="#"><i class="fas fa-user"></i> <fmt:message key="admin.menu.student-report"/></a></li>
                <li><a href="#"><i class="fas fa-users"></i> <fmt:message key="admin.menu.students-report"/></a></li>

            </ul>

            <hr>
            <strong><i class="fas fa-align-justify"></i> <fmt:message key="admin.menu.tests"/></strong>
            <hr>
            <ul class="nav nav-stacked">
                <li><a href="/admin/add_test.jsp"><i class="fas fa-pencil-alt"></i>
                    <fmt:message key="admin.menu.add-test"/></a></li>
                <li><a href="/admin/remove_test.jsp"><i class="fas fa-trash-alt"></i> <fmt:message
                        key="admin.menu.remove-test"/></a></li>
            </ul>

            <hr>
            <strong><i class="far fa-question-circle"></i> <fmt:message
                    key="admin.menu.questions"/></strong>
            <hr>
            <ul class="nav nav-stacked">

                <li><a href="/admin/add_question.jsp"><i class="fas fa-list-ol"></i>
                    <fmt:message
                            key="admin.menu.add-question"/></a>
                </li>
                <li><a href="/admin/remove_question.jsp"><i class="fas fa-eraser"></i> <fmt:message
                        key="admin.menu.remove-question"/></a></li>
            </ul>
        </div>
        <div class="col-sm-9">
            <div class="content">
                <form action="/remove_test" method="get">
                    <div class="form-group">
                        <label for="test_name"><fmt:message key="admin.add-test.label.test-name"/></label>
                        <input id="test_name" name="test_name" type="text" class="form-control here"
                               aria-describedby="textHelpBlock">
                    </div>
                    <div class="form-group">
                        <label for="description"><fmt:message key="admin.add-test.label.description"/></label>
                        <textarea id="description" name="description" cols="40" rows="5"
                                  class="form-control"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="test" class="col-4 col-form-label">Выберите тест:</label>
                        <select id="test" name="test" class="selectpicker form-control" data-live-search="true">
                            <c:forEach items="${all_tests}" var="test">
                                <option value="${test.getId()}">${test.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>


                    <c:if test="${succsess}">
                        <div class="alert alert-success">
                            <fmt:message key="admin.add-test.succsess"/>
                        </div>
                    </c:if>
                    <c:if test="${test_exist}">
                        <div class="alert alert-danger">
                            <fmt:message key="admin.add-test.test-exist-error"/>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <button name="submit" type="submit" class="btn btn-primary pull-right"><fmt:message
                                key="admin.add-test.button.submit"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<footer class="text-center">2018, <strong>Grind3x</strong></footer>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>
