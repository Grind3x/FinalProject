<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <title>Login page</title>
</head>
<style>
    <%@include file="/WEB-INF/style/login.css" %>
</style>
<body>
<div class="container">

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link"><fmt:message key="login.label.login"/></a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link"><fmt:message key="login.label.register"/></a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="/login" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <input type="text" name="email" id="email" tabindex="1" class="form-control"
                                           placeholder="<fmt:message key="login.placeholder.email"/>" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2"
                                           class="form-control"
                                           placeholder="<fmt:message key="login.placeholder.password"/>">
                                </div>
                                <div class="form-group text-center">
                                    <c:if test="${msg ne null}">
                                        <div class="alert alert-success">
                                            <c:out value="${msg}"/>
                                        </div>
                                    </c:if>

                                    <c:if test="${error}">
                                        <div class="alert alert-danger">
                                            <fmt:message key="login.alert.incorrect-email-password"/>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                                   class="form-control btn btn-login"
                                                   value="<fmt:message key="login.button.submit"/> ">
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form" action="/register" method="post" role="form"
                                  style="display: none;">
                                <div class="form-group">
                                    <input type="text" name="full_name" id="full_name" tabindex="1" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.fullname"/>" value="">
                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.email"/>" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2"
                                           class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.password"/>">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="confirm_password" id="confirm_password" tabindex="2"
                                           class="form-control"
                                           placeholder="<fmt:message key="register.placeholder.password-confirm"/>">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-register"
                                                   value="<fmt:message key="register.button.submit"/>">
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form action="/">
                                <select class="form-control" id="language" name="language" onchange="submit()">
                                    <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                                    <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                                    <option value="es" ${language == 'es' ? 'selected' : ''}>Ураїнська</option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
<script>
    $(function () {
        $('#login-form-link').click(function (e) {
            $("#login-form").delay(100).fadeIn(100);
            $("#register-form").fadeOut(100);
            $('#register-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
        $('#register-form-link').click(function (e) {
            $("#register-form").delay(100).fadeIn(100);
            $("#login-form").fadeOut(100);
            $('#login-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
    });
</script>
</html>
