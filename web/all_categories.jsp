<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty lang ? lang : sessionScope.language}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Система быстрого тестирования - Главная</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="generator" content="Bootply"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
          integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
</head>
<style>
    <%@include file="/WEB-INF/style/bootstrap.min.css" %>
    <%@include file="/WEB-INF/style/styles.css" %>
    footer {
        position: absolute;
        height: 50px;
        bottom: 0;
        width: 100%;
    }

</style>
<body>
<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/tests"><fmt:message key="categories.home"/></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><p class="navbar-text pull-right">
                    Вы вошли как ${user.getFullName()}
                </p></li>
                <c:if test="${user.getRole().getName() eq 'admin'}">
                    <li><a href="/admin/"><i class="fas fa-screwdriver"></i> Управление</a></li>
                </c:if>
                <li><a href="/logout"><i class="fas fa-sign-out-alt"></i> Выйти</a></li>
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
                    ${language}
                </div>
                <div class="panel-body" style="background-color: rgba(215,236,255,0.37)">
                    <div class="container-fluid">
                        <c:set var="i" value="1"/>
                        <c:set var="rowOpened" value="false"/>
                        <div class="panel-group">
                            <c:forEach items="${categories}" var="category">
                                <c:if test="${i%3 ne 0 and !rowOpened}">
                                    <div class="row equal-height-panels">
                                    <c:set var="rowOpened" value="true"/>
                                </c:if>
                                <div class="col-sm-4">
                                    <div class="panel">
                                        <div class="panel-heading">
                                            <h4 class="text-center">${category.getName()}</h4>
                                        </div>
                                        <div class="panel-body">
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
</div>

</div>


<script>

    $(document).ready(function () {
        $('.equal-height-panels .panel').matchHeight();
    });
</script>

<footer class="text-center">2018, <strong>Grind3x</strong></footer>

</body>
</html>


















