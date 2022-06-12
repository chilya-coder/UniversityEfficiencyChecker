<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Please sign in</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
    <link href="/resources/login.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body class="d-flex flex-column min-vh-100 mt-5" style="padding-bottom: 0px">
<%
    if ("true".equals(request.getParameter("error"))) {%>
<script>
    $(document).ready(function () {
        document.getElementById('username').style.backgroundColor = 'pink';
        document.getElementById('password').style.backgroundColor = 'pink';
        alert("Користувача з таким логіном та паролем не знайдено!");
    })
</script>
<%}%>
<div class="container">
    <section class="vh-100">
        <div class="container-fluid h-custom">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-md-9 col-lg-6 col-xl-5 mb-4">
                    <img src="/resources/images/sumdu_logo.png"
                         class="img-fluid" width="350" height="100">
                </div>
                <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1 pt-5 mt-5">
                    <form method="post" action="/login">
                        <div class="form-outline mb-4">
                            <input type="text" id="username" class="form-control form-control-lg"
                                   placeholder="Логін" name="username"/>
                            <label class="form-label" for="username">Введіть логін</label>
                        </div>

                        <!-- Password input -->
                        <div class="form-outline mb-3">
                            <input type="password" id="password" class="form-control form-control-lg"
                                   placeholder="Пароль" name="password"/>
                            <label class="form-label" for="password">Введіть пароль</label>
                        </div>

                        <div class="text-center text-lg-start mt-4 pt-1">
                            <button type="submit" class="btn btn-primary btn-lg"
                                    style="padding-left: 2.5rem; padding-right: 2.5rem; background-color: rgb(12,140,203)">
                                Ввійти
                            </button>
                            <p class="large fw-bold mt-2 pt-0 mb-5">Ще не зареєтровані? <a href="/register" class="link-danger">Зареєструватися</a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<section>
    <footer class="text-center text-white mt-5" style="background-color: #0a4275;">
        <div class="container">
            <section class="">
                <p class="justify-content-center" style="margin-bottom: 0px; margin-top: 0px;">
                    <span>Розробник: Чімирис Юлія</span>
                </p>
            </section>
        </div>
        <div class="text-center p-3 " style="background-color: rgba(0, 0, 0, 0.2);">
            © 2022:
            <a class="text-white" href="https://mdbootstrap.com/">Сумський державний університет</a>
        </div>
        <!-- Copyright -->
    </footer>
    <!-- Footer -->
</section>
</html>