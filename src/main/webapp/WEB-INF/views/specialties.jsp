<%@ page import="com.chimyrys.universityefficiencychecker.db.RequirementTypeRepository" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.RequirementType" %>
<%@ page import="java.util.List" %>
<%@ page import="com.chimyrys.universityefficiencychecker.services.api.UserService" %>
<%@ page import="com.chimyrys.universityefficiencychecker.db.SpecialtyRepository" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.Specialty" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Cписок спеціальностей</title>
    <link href="/resources/user_profile.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {

            const btn = document.querySelector('#add-btn');
            const modal = new bootstrap.Modal(document.querySelector('#modal'));
            btn.addEventListener('click', function () {
                modal.show();
            });
            const closeButtons = document.querySelectorAll('.btn-close');
            closeButtons.forEach(function (closeBtn) {
                closeBtn.addEventListener('click', function () {
                    modal.hide();
                })
            });
            const saveButton = document.querySelector('#save-specialty');
            saveButton.addEventListener('click', function () {
                var formData = new FormData(document.querySelector('#add-specialty-form'));
                var requestBody = {};
                formData.forEach((value, key) => requestBody[key] = value);
                var json = JSON.stringify(requestBody);
                $.ajax({
                    type: "POST",
                    url: "/admin/specialties",
                    data: json,
                    dataType: 'json',
                    contentType: "application/json",
                    complete: [
                        function (response) {
                            document.location.reload();
                        }
                    ]
                })
            });
        });
    </script>
</head>
<%
    SpecialtyRepository specialtyRepository = (SpecialtyRepository) request.getAttribute("specialtyRepository");
    UserService userService = (UserService) request.getAttribute("userService");
    List<Specialty> specialties = specialtyRepository.findAll();
%>
<body>
<nav class="navbar navbar-light bg-light" id="nav">
    <div class="container-fluid">
        <a class="navbar-brand" href="/user_profile">
            <img src="/resources/images/logo.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
            <%=userService.getCurrentUser().getFullName()%>
        </a>
    </div>
</nav>
<div class="container">
    <div class="container">
        <div class="row justify-content-between">
            <div class="col-auto">
                <input class="btn btn-primary mb-3" id="add-btn" type="button" value="Додати спеціальність" style="background-color: rgb(12,140,203)">
            </div>
            <input class="col-auto btn btn-secondary mb-3" type="button" onclick="location.href = '/perform_logout'" value="Вихід">
        </div>
    </div>
</div>
<div class="container">
    <table class="table table-bordered mt-5">
        <thead>
        <tr>
            <th scope="col">№</th>
            <th scope="col">Назва спеціальності</th>
            <th scope="col">Код спеціальності</th>
            <th scope="col">Аліас спеціальності</th>
        </tr>
        </thead>
        <tbody>
        <%for (int i = 0; i < specialties.size(); ++i) {%>
        <tr>
            <th scope="row"><%=i + 1%></th>
            <th scope="row"><%=specialties.get(i).getName()%></th>
            <td scope="row"><%=specialties.get(i).getCode()%></td>
            <td scope="row"><%=specialties.get(i).getSpecialtyAlias()%></td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Додати нову спеціальність</h5>
                <button type="button" class="btn-close btn btn-secondary" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="add-specialty-form">
                    <div class="mb-1 py-2">
                        <label class="form-label" for="specialty-name">Назва спеціальності</label>
                        <input type="text" id="specialty-name" name="name" class="form-control form-control-lg"/>
                    </div>
                    <div class="mb-1 py-2">
                        <label class="form-label" for="specialty-code">Код спеціальності</label>
                        <input type="number" id="specialty-code" name="code" class="form-control form-control-lg"/>
                    </div>
                    <div class="mb-1 py-2">
                        <label class="form-label" for="specialty-alias">Аліас спеціальності</label>
                        <input type="text" id="specialty-alias" name="alias" class="form-control form-control-lg"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="save-specialty" class="btn btn-info" style="background-color: rgb(12,140,203)">Зберегти</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
