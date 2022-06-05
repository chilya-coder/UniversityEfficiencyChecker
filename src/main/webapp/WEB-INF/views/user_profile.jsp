<%@ page import="com.chimyrys.universityefficiencychecker.model.User" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/resources/user_profile.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<%
    User user = (User) request.getAttribute("user");
%>
<script>
    function doEdit() {
        document.getElementById('first-name').disabled = false;
        document.getElementById('submit-button').hidden = false;
        document.getElementById('edit-button').hidden = true;
    }
</script>
<div class="container">
    <div class="main-body">
        <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li class="breadcrumb-item"><a href="javascript:void(0)">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">User Profile</li>
            </ol>
        </nav>
        <form:form modelAttribute ="user1" method="post" action="/user_profile">
            <div class="row gutters-sm">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <img src="/resources/images/logo.png" class="rounded-circle" width="150">
                                <div class="mt-3">
                                    <h4><%=user.getFirstName() + " " + user.getLastName()%>
                                    </h4>
                                    <p class="text-secondary mb-1"><%=user.getPosition().getName()%>
                                    </p>
                                    <p class="text-secondary mb-1"><%=user.getDepartment().getName()%>
                                    </p>
                                    <p class="text-muted font-size-sm"><%=user.getEmail()%>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">ПІБ</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <input class="text-secondary mb-1" id="first-name" name="first-name" placeholder=
                                    "<%=user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic()%>" disabled/>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Емейл</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getEmail()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Позиція</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getPosition().getName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Кафедра</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getDepartment().getName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Факультет</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getDepartment().getFaculty().getName()%>
                                </div>
                            </div>
                            <hr>
                            <!--
                            <div class="row">
                                <div class="col-sm-12">
                                    <input value="Edit" type="button" class="btn btn-default" id="edit-button" onclick="doEdit()">
                                </div>
                                <div class="col-sm-12">
                                    <input value="Submit" type="submit" class="btn btn-default" id="submit-button" hidden="true">
                                </div>
                            </div>
                            -->
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
