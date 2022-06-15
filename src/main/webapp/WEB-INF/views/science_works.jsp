<%@ page import="com.chimyrys.universityefficiencychecker.model.ScienceWork" %>
<%@ page import="java.util.List" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.TypeOfWork" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.CharOfWork" %>
<%@ page import="com.chimyrys.universityefficiencychecker.services.api.UserService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.chimyrys.universityefficiencychecker.db.SpecialtyRepository" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.Specialty" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Наукові роботи</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="/resources/science_works.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
            })
            const saveButton = document.querySelector('#save-science-work');
            saveButton.addEventListener('click', function () {
                var formData = new FormData(document.querySelector('#add-science-work-form'));
                var requestBody = {};
                formData.forEach((value, key) => requestBody[key] = value);
                var json = JSON.stringify(requestBody);
                $.ajax({
                    type: "POST",
                    url: "/science_work",
                    data: json,
                    dataType: 'json',
                    contentType: "application/json",
                    complete: [
                        function (response) {
                            document.location.reload();
                        }
                    ]
                })
            })
            const sortButton = document.querySelector('#sort-btn');
            const confirmSortButton = document.querySelector('#confirm-sort-btn');
            sortButton.addEventListener('click', function () {
                if (document.getElementById('sort-data').hidden === false) {
                    document.getElementById('sort-data').hidden = true;
                } else {
                    document.getElementById('sort-data').hidden = false;
                }
            })
            confirmSortButton.addEventListener('click', function () {
                var date_from = document.querySelector("#date-from").value;
                var date_to = document.querySelector("#date-to").value;
                if (date_from > date_to || date_from === date_to) {
                    document.getElementById('date-from').style.backgroundColor = 'pink';
                    document.getElementById('date-to').style.backgroundColor = 'pink';
                    confirmSortButton.disabled = true;
                }
                document.location.href = "/filter_science_work?since=" + date_from + "&to=" + date_to;
            })
            const date_inputs = document.querySelectorAll(".date-input");
            date_inputs.forEach(function (date_input) {
                date_input.addEventListener("focusout", function () {
                    var date_from = document.querySelector("#date-from").value;
                    var date_to = document.querySelector("#date-to").value;
                    if (date_from === ''
                        || date_to === '') {
                        return;
                    }
                    if ((date_from > date_to || date_from === date_to) && (date_from !== ''
                        && date_to !== '')) {
                        document.getElementById('date-from').style.backgroundColor = 'pink';
                        document.getElementById('date-to').style.backgroundColor = 'pink';
                        confirmSortButton.disabled = true;
                        alert("Дата до повинна бути раніше дати після");
                    } else {
                        document.getElementById('date-from').style.backgroundColor = 'white';
                        document.getElementById('date-to').style.backgroundColor = 'white';
                        confirmSortButton.disabled = false;
                    }
                });
            })

        });

        function deleteScienceWork(id) {
            if (confirm('Ви бажаєте видалити обрану публікацію?')) {
                $.ajax({
                    type: "DELETE",
                    url: "/science_work?id=" + id,
                    complete: [
                        function (response) {
                            document.location.reload();
                        }
                    ]
                })
            } else {
                return;
            }
        }
    </script>
</head>
<%
    List<ScienceWork> scienceWorkList = (List<ScienceWork>) request.getAttribute("science_works");
    UserService userService = (UserService) request.getAttribute("userService");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    SpecialtyRepository specialtyRepository = (SpecialtyRepository) request.getAttribute("specialtyRepository");
%>
<body>
<nav class="navbar navbar-light bg-light" id="nav">
    <div class="container-fluid">
        <a class="navbar-brand" href="/user_profile">
            <img src="resources/images/logo.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
            <%=userService.getCurrentUser().getFullName()%>
        </a>
    </div>
</nav>
<div class="container">
    <div class="flex-box row">
        <button class="btn btn-primary mt-4" id="add-btn" data-bs-toggle="modal" data-bs-target="#modal"
                style="background-color: rgb(12,140,203)">Додати публікацію
        </button>
        <button class="btn btn-primary mt-4" id="sort-btn" style="background-color: rgb(12,140,203)">Фільтрація за
            роком
        </button>
        <%if (request.getParameter("since") != null && !request.getParameter("since").isEmpty()) {%>
        <input type="button" class="btn btn-primary mt-4" value="Згенерувати список праць"
               style="background-color: rgb(12,140,203)"
               onclick="location.href='/generate_report?since=<%=request.getParameter("since")%>&to=<%=request.getParameter("to")%>'">
        <%} else {%>
        <input type="button" class="btn btn-primary mt-4" value="Згенерувати список праць"
               style="background-color: rgb(12,140,203)" onclick="location.href='/generate_report'">
        <%}%>
        <input type="button" class="btn btn-primary mt-4" value="Інформаційна довідка"
               style="background-color: rgb(12,140,203)" onclick="location.href='/generate_info_report'">
    </div>
    <div class="mb-1 py-2" id="sort-data" hidden="true">
        <label class="form-label"
               for="date-from">Публікації з</label>
        <input type="number" min="2000" max="2050" step="1" id="date-from" name="date-from"
               class="form-control form-control-lg w-25 date-input"/>
        <label class="form-label"
               for="date-to">до</label>
        <input type="number" min="2000" max="2050" step="1" id="date-to" name="date-to"
               class="form-control form-control-lg w-25 date-input"/>
        <button class="btn btn-primary mt-4" id="confirm-sort-btn" style="background-color: rgb(12,140,203)" disabled>
            Відфільтрувати
        </button>
    </div>
    <table class="table table-bordered mt-5">
        <thead>
        <tr>
            <th scope="col">№ з/п</th>
            <th scope="col">ID</th>
            <th scope="col">Назва</th>
            <th scope="col">Характер роботи</th>
            <th scope="col">Вихідні дані</th>
            <th scope="col">Обсяг сторінок</th>
            <th scope="col">Співавтори</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%for (int i = 0; i < scienceWorkList.size(); ++i) {%>
        <tr>
            <th scope="row"><%=i + 1%>
            </th>
            <th scope="row"><%=scienceWorkList.get(i).getScienceWorkId()%>
            </th>
            <th scope="row"><%=scienceWorkList.get(i).getName() + "(" + scienceWorkList.get(i).getTypeOfWork().getType().toLowerCase() + ")"%>
            </th>
            <td scope="row"><%=scienceWorkList.get(i).getCharOfWork().getCharacteristic()%>
            </td>
            <td scope="row"><%=scienceWorkList.get(i).getOutputData() + "<br/>Дата публікації: "
                    + simpleDateFormat.format(scienceWorkList.get(i).getDateOfPublication())%>
            </td>
            <td scope="row"><%=scienceWorkList.get(i).getSize()%>
            </td>
            <td scope="row"><%=scienceWorkList.get(i).getCoAuthorsNames(userService.getCurrentUser())%>
            </td>
            <td scope="row">
                <button class="btn btn-primary" style="background-color: rgb(12,140,203)">Редагувати</button>
            </td>
            <td scope="row">
                <button class="btn btn-danger" id="<%=scienceWorkList.get(i).getScienceWorkId()%>"
                        onclick="deleteScienceWork(id)">X
                </button>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
    <div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Додати нову публікацію</h5>
                    <button type="button" class="btn-close btn btn-secondary" data-bs-dismiss="modal"
                            aria-label="Close">x
                    </button>
                </div>
                <div class="modal-body">
                    <form id="add-science-work-form">
                        <div class="mb-1 py-2">
                            <label class="form-label"
                                   for="science-work-name">Назва роботи*</label>
                            <input type="text" id="science-work-name" name="name"
                                   class="form-control form-control-lg"/>
                        </div>
                        <div class="mb-1 py-2">
                            <label for="type-of-work-id">Тип роботи*</label>
                            <select id="type-of-work-id" name="typeOfWorkId" class="form-select mb-3">
                                <%for (TypeOfWork t : TypeOfWork.values()) {%>
                                <option value="<%=t.getId()%>"><%=t.getType()%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                        <div class="mb-1 py-2">
                            <label for="char-of-work-id">Характер роботи*</label>
                            <select id="char-of-work-id" name="charOfWorkId" class="form-select mb-3">
                                <%for (CharOfWork charOfWork : CharOfWork.values()) {%>
                                <option value="<%=charOfWork.getId()%>"><%=charOfWork.getCharacteristic()%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                        <div class="mb-1 py-2">
                            <label for="specialty-id">Спеціальність*</label>
                            <select id="specialty-id" name="specialtyId" class="form-select mb-3">
                                <%for (Specialty specialty : specialtyRepository.findAll()) {%>
                                <option value="<%=specialty.getSpecialtyId()%>"><%=specialty.getName()%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                        <div class="mb-1 py-2">
                            <label class="form-label" for="outputData">Вихідні дані*</label>
                            <input type="text" id="outputData" name="outputData"
                                   class="form-control form-control-lg"/>
                        </div>
                        <div>
                            <label class="form-label" for="dateOfPublication">Дата публікації*</label>
                            <input type="date" id="dateOfPublication" name="dateOfPublication"
                                   class="form-control form-control-lg">
                        </div>
                        <div class="mb-1 py-2">
                            <label class="form-label" for="size">Обсяг(стор.)*</label>
                            <input type="number" id="size" name="size" min="1"
                                   class="form-control form-control-lg"/>
                        </div>
                        <div class="mb-1 py-2">
                            <label class="form-label" for="extTeacherName">Співавтори: викладачі</label>
                            <input type="text" id="extTeacherName" name="extTeacherName"
                                   class="form-control form-control-lg"/>
                            <small id="extTeacherNameHelp" class="form-text text-muted">Введіть ім'я викладачів у
                                форматі: Прізвище Ім'я По-батькові, Прізвище Ім'я По-батькові</small>
                        </div>
                        <div class="mb-1 py-2">
                            <label class="form-label" for="extStudentName">Співавтори: студенти</label>
                            <input type="text" id="extStudentName" name="extStudentName"
                                   class="form-control form-control-lg"/>
                            <small id="extStudentNameHelp" class="form-text text-muted">Введіть ім'я студентів у
                                форматі: Прізвище Ім'я По-батькові(Група), Прізвище Ім'я По-батькові(Група)</small>
                        </div>
                        <div class="mb-1 py-2">
                            <label class="form-label" for="extAuthorsName">Співавтори: зовнішні автори</label>
                            <input type="text" id="extAuthorsName" name="extAuthorsName"
                                   class="form-control form-control-lg"/>
                            <small id="extAuthorsNameHelp" class="form-text text-muted">Введіть ім'я зовнішніх авторів у
                                форматі: Прізвище Ім'я По-батькові, Прізвище Ім'я По-батькові</small>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" id="save-science-work" class="btn btn-info"
                            style="background-color: rgb(12,140,203)">Зберегти
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
