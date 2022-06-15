<%@ page import="com.chimyrys.universityefficiencychecker.db.ContractRepository" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Optional" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.chimyrys.universityefficiencychecker.db.SpecialtyRepository" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/resources/user_profile.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

</head>
<body>
<%
    User user = (User) request.getAttribute("user");
    SpecialtyRepository specialtyRepository = (SpecialtyRepository) request.getAttribute("specialtyRepository");
    ContractRepository contractRepository = (ContractRepository) request.getAttribute("contractRepository");
    List<Contract> contracts = user.getContracts().stream().sorted((o1, o2) -> -1 * o1.getDateEnd().compareTo(o2.getDateEnd())).collect(Collectors.toList());
    List<Specialty> specialties = contracts.stream().map(Contract::getSpecialty).distinct().collect(Collectors.toList());
    Boolean isUserEnabled = (Boolean) request.getAttribute("isUserEnabled");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
%>
<nav class="navbar navbar-light bg-light" id="nav">
    <div class="container-fluid">
        <a class="navbar-brand" href="/user_profile">
            <img src="/resources/images/logo.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
            <%=user.getFullName()%>
        </a>
    </div>
</nav>
<script>
    function doEdit() {
        document.getElementById('first-name').disabled = false;
        document.getElementById('submit-button').hidden = false;
        document.getElementById('edit-button').hidden = true;
    }

    function adminPanel() {
        alert("<%isUserEnabled.toString();%>");
    }

    document.addEventListener('DOMContentLoaded', function () {
        const btn = document.querySelector('#add-contract-button');
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
        const saveButton = document.querySelector('#save-contract');
        saveButton.addEventListener('click', function () {
            var formData = new FormData(document.querySelector('#add-contract-form'));
            var requestBody = {};
            formData.forEach((value, key) => requestBody[key] = value);
            var json = JSON.stringify(requestBody);
            $.ajax({
                type: "POST",
                url: "/user_profile",
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
    });
</script>
<div class="container">
    <div class="main-body">
        <div class="container">
            <div class="row justify-content-between">
                <div class="col-auto">
                    <input class="btn btn-primary mb-3" type="button" onclick="location.href = '/science_work'" value="Наукові роботи" style="background-color: rgb(12,140,203)">
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <input class="btn btn-info mb-3" type="button" onclick="location.href = '/admin/admin_tools'" value="Адміністративна панель">
                        <input class="btn btn-info mb-3" type="button" onclick="location.href = '/admin/specialties'" value="Список спеціальностей">
                    </sec:authorize>
                </div>
                <input class="col-auto btn btn-secondary mb-3" type="button" onclick="location.href = '/perform_logout'"
                       value="Вихід">
            </div>
        </div>
        <form:form modelAttribute="user1" method="post" action="/user_profile">
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
                                    <h6 class="mb-0">ПІБ:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary" id="first-name" name="first-name">
                                    <%=user.getFullName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Емейл:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getEmail()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Посада:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getPosition().getName()%>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Вчений ступінь:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getDegree().getName()%>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Наукове звання:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getScientificTitle().getName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Кафедра:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getDepartment().getName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Факультет:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%=user.getDepartment().getFaculty().getName()%>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Термін попереднього контракту:</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <%if (!contracts.isEmpty()) {%>
                                    <p>
                                        <%for(Specialty specialty : specialties) {
                                        Contract contract = contracts.stream().filter(x -> x.getSpecialty() == specialty).findFirst().get();%>
                                        з <%=simpleDateFormat.format(contract.getDateStart())%> р. по <%=simpleDateFormat.format(contract.getDateEnd())%> р. (<%=contract.getSpecialty().getSpecialtyAlias()%>)</br>
                                        <%}%>
                                    </p>
                                    <%} else {%>
                                    Контракт відсутній
                                    <%}%>
                                    <input class="btn btn-primary mb-3" type="button" id="add-contract-button"
                                           value="Додати контракт" style="background-color: rgb(12,140,203)">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Додати новий контракт</h5>
                <button type="button" class="btn-close btn btn-secondary" data-bs-dismiss="modal"
                        aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <form id="add-contract-form">
                    <div class="mb-1 py-2">
                        <label class="form-label"
                               for="start-contract-date">Початок контракту*</label>
                        <input type="date" id="start-contract-date" name="start-contract-date"
                               class="form-control form-control-lg" required/>
                    </div>
                    <div class="mb-1 py-2">
                        <label class="form-label"
                               for="end-contract-date">Кінець контракту*</label>
                        <input type="date" id="end-contract-date" name="end-contract-date"
                               class="form-control form-control-lg" required/>
                    </div>
                    <div class="mb-1 py-2">
                        <label for="specialty-id">Спеціальність</label>
                        <select id="specialty-id" name="specialtyId" class="form-select mb-3"
                                aria-label=".form-select example">
                            <%for (Specialty s : specialtyRepository.findAll()) {%>
                            <option value="<%=s.getSpecialtyId()%>"><%=s.getName()%>
                            </option>
                            <%}%>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="save-contract" class="btn btn-primary"
                        style="background-color: rgb(12,140,203)">Зберегти
                </button>
            </div>
        </div>
    </div>
</div>
<section>
    <footer class="text-center text-white" style="background-color: #0a4275;">
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
    </footer>
</section>
</body>
</html>
