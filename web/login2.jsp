<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
</head>
<style>
    <%@include file="/style/login2.css" %>
</style>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-5 mx-auto">
            <div id="first">
                <div class="myform form ">
                    <div class="logo mb-3">
                        <div class="col-md-12 text-center">
                            <h1>Login</h1>
                        </div>
                    </div>
                    <form action="" method="post" name="login">
                        <div class="form-group">
                            <label for="email">Email address</label>
                            <input type="email" name="email" class="form-control" id="email"
                                   aria-describedby="emailHelp" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" name="password" id="password" class="form-control"
                                   aria-describedby="emailHelp" placeholder="Enter Password">
                        </div>

                        <div class="col-md-12 text-center ">
                            <button type="submit" class=" btn btn-block mybtn btn-primary tx-tfm">Login</button>
                        </div>
                        <div class="form-group">
                            <p class="text-center">Don't have account? <a href="#" id="signup">Sign up here</a></p>
                        </div>
                    </form>

                </div>
            </div>
            <div id="second">
                <div class="myform form ">
                    <div class="logo mb-3">
                        <div class="col-md-12 text-center">
                            <h1>Signup</h1>
                        </div>
                    </div>
                    <form action="#r" name="registration">
                        <div class="form-group">
                            <label for="firstname">First Name</label>
                            <input type="text" name="firstname" class="form-control" id="firstname"
                                   aria-describedby="emailHelp" placeholder="Enter Firstname">
                        </div>
                        <div class="form-group">
                            <label for="lastname">Last Name</label>
                            <input type="text" name="lastname" class="form-control" id="lastname"
                                   aria-describedby="emailHelp" placeholder="Enter Lastname">
                        </div>
                        <div class="form-group">
                            <label for="email">Email address</label>
                            <input type="email" name="email" class="form-control" id="email"
                                   aria-describedby="emailHelp" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" name="password" id="password" class="form-control"
                                   aria-describedby="emailHelp" placeholder="Enter Password">
                        </div>
                        <div class="col-md-12 text-center mb-3">
                            <button type="submit" class=" btn btn-block mybtn btn-primary tx-tfm">Get Started For Free
                            </button>
                        </div>
                        <div class="col-md-12 ">
                            <div class="form-group">
                                <p class="text-center"><a href="#" id="signin">Already have an account?</a></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        $("#signup").click(function () {
            $("#first").fadeOut("fast", function () {
                $("#second").fadeIn("fast");
            });
        });

        $("#signin").click(function () {
            $("#second").fadeOut("fast", function () {
                $("#first").fadeIn("fast");
            });
        });

        $(function () {
            $("form[name='login']").validate({
                rules: {

                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,

                    }
                },
                messages: {
                    email: "Please enter a valid email address",

                    password: {
                        required: "Please enter password",

                    }

                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
        });


        $(function () {

            $("form[name='registration']").validate({
                rules: {
                    firstname: "required",
                    lastname: "required",
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 5
                    }
                },

                messages: {
                    firstname: "Please enter your firstname",
                    lastname: "Please enter your lastname",
                    password: {
                        required: "Please provide a password",
                        minlength: "Your password must be at least 5 characters long"
                    },
                    email: "Please enter a valid email address"
                },

                submitHandler: function (form) {
                    form.submit();
                }
            });
        });

    </script>

</body>
</html>
