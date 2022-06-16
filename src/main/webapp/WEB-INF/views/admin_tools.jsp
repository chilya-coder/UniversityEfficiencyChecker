<%@ page import="com.chimyrys.universityefficiencychecker.db.RequirementTypeRepository" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.RequirementType" %>
<%@ page import="java.util.List" %>
<%@ page import="com.chimyrys.universityefficiencychecker.services.api.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Адміністративна панель</title>
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
            const saveButton = document.querySelector('#save-requirement-type');
            saveButton.addEventListener('click', function () {
                var formData = new FormData(document.querySelector('#add-requirement-type-form'));
                var requestBody = {};
                formData.forEach((value, key) => requestBody[key] = value);
                var json = JSON.stringify(requestBody);
                $.ajax({
                    type: "POST",
                    url: "/admin/requirement_type",
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
    RequirementTypeRepository requirementTypeRepository = (RequirementTypeRepository) request.getAttribute("requirementTypeRepository");
    UserService userService = (UserService) request.getAttribute("userService");
    List<RequirementType> requirementTypeList = requirementTypeRepository.findAll();
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
                <input class="btn btn-primary mb-3" id="add-btn" type="button" value="Додати ліцензійну вимогу" style="background-color: rgb(12,140,203)">
                <input class="btn btn-primary mb-3" id="generate-report-btn" type="button" value="Згенерувати зведену документацію" onclick="location.href = '/admin/activity_report'" style="background-color: rgb(12,140,203)">
            </div>
            <input class="col-auto btn btn-secondary mb-3" type="button" onclick="location.href = '/perform_logout'"
                   value="Вихід">
        </div>
    </div>
</div>
<div class="container">
    <table class="table table-bordered mt-5">
        <thead>
        <tr>
            <th scope="col">№</th>
            <th scope="col">Назва ліц. вимоги</th>
            <th scope="col">Номер ліц. вимоги</th>
        </tr>
        </thead>
        <tbody>
        <%for (int i = 0; i < requirementTypeList.size(); ++i) {%>
        <tr>
            <th scope="row"><%=i + 1%></th>
            <th scope="row"><%=requirementTypeList.get(i).getName()%></th>
            <td scope="row"><%=requirementTypeList.get(i).getReqNumber()%></td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Додати нову ліцензійну вимогу</h5>
                <button type="button" class="btn-close btn btn-secondary" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="add-requirement-type-form">
                    <div class="mb-1 py-2">
                        <label class="form-label" for="requirement-type-name">Назва вимоги</label>
                        <input type="text" id="requirement-type-name" name="name" class="form-control form-control-lg"/>
                    </div>
                    <div class="mb-1 py-2">
                        <label class="form-label" for="requirement-type-number">Номер вимоги</label>
                        <input type="number" id="requirement-type-number" name="number" class="form-control form-control-lg"/>
                    </div>
                    <div class="mb-1 py-2">
                        <label class="form-label" for="requirement-type-condition">Умова вимоги</label>
                        <input type="text" id="requirement-type-condition" name="condition" class="form-control form-control-lg"/>
                        <small id="extTeacherNameHelp" class="form-text text-muted">Введіть умову за прикладом: вихідні дані contains 'scopus'</small>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="save-requirement-type" class="btn btn-info" style="background-color: rgb(12,140,203)">Зберегти</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
