<%@ page import="java.util.List" %>
<%@ page import="com.chimyrys.universityefficiencychecker.db.*" %>
<%@ page import="com.chimyrys.universityefficiencychecker.model.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Реєстрація</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link href="/resources/register.css" rel="stylesheet"/>
    <link href="/resources/main_style.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
<%
    DepartmentRepository departmentRepository = (DepartmentRepository) request.getAttribute("departmentRepository");
    List<Department> departmentList = departmentRepository.findAll();
    PositionRepository positionRepository = (PositionRepository) request.getAttribute("positionRepository");
    List<Position> positionList = positionRepository.findAll();
    ScientificTitleRepository scientificTitleRepository = (ScientificTitleRepository) request.getAttribute("scientificTitleRepository");
    List<ScientificTitle> scientificTitles = scientificTitleRepository.findAll();
    DegreeRepository degreeRepository = (DegreeRepository) request.getAttribute("degreeRepository");
    List<Degree> degreeList = degreeRepository.findAll();

%>
<div class="mask d-flex align-items-center bg-image" id="container-register">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                <div class="card" style="border-radius: 15px;">
                    <div class="card-body p-5">
                        <h4 class="text-uppercase text-center mb-5">Створити нового користувача</h4>
                        <form:form modelAttribute="userCredential" method="post" action="/register">
                            <div class="pre-form text mb-3">Будь ласка, заповніть дані нижче для перевірки
                                адміністратором.
                            </div>
                            <div class="mb-1 py-2">
                                <form:label path="user.firstName" class="form-label"
                                            for="validation-name">Ім'я</form:label>
                                <form:input path="user.firstName" type="text" id="validation-name"
                                            class="form-control form-control-lg"/>
                                <form:errors path="user.firstName" class="error-msg"/>
                            </div>

                            <div class="mb-1 py-2">
                                <form:label path="user.lastName" class="form-label"
                                            for="surname">Прізвище</form:label>
                                <form:input path="user.lastName" type="text" id="surname"
                                            class="form-control form-control-lg"/>
                                <form:errors path="user.lastName" class="error-msg"/>
                            </div>

                            <div class="mb-1 py-2">
                                <form:label path="user.patronymic" class="form-label"
                                            for="patronymic">По батькові</form:label>
                                <form:input path="user.patronymic" type="text" id="patronymic"
                                            class="form-control form-control-lg"/>
                                <form:errors path="user.patronymic" class="error-msg"/>
                            </div>

                            <div class="mb-1 py-2">
                                <form:label path="user.email" class="form-label"
                                            for="email">Електронна пошта</form:label>
                                <form:input path="user.email" type="email" id="email"
                                            class="form-control form-control-lg"/>
                                <form:errors path="user.email" class="error-msg"/>
                                <small id="emailHelp" class="form-text text-muted">Н-п: test@gmail.com</small>
                            </div>

                            <div class="mb-1 py-2">
                                <form:label path="login" class="form-label" for="login">Логін</form:label>
                                <form:input path="login" type="text" id="login"
                                            class="form-control form-control-lg"/>
                                <form:errors path="login" class="error-msg"/>
                            </div>

                            <div class="mb-1 py-2">
                                <form:label path="password" class="form-label" for="password">Пароль</form:label>
                                <form:input path="password" type="password" id="password"
                                            class="form-control form-control-lg"/>
                                <form:errors path="password" class="error-msg"/>
                                <small id="passwordlHelp" class="form-text text-muted">Пароль повинен містити від 8
                                    до 15 символів</small>
                            </div>

                            <div class="mb-1 py-2">
                                <label for="departmentId">Кафедра</label>
                                <select id="departmentId" name="departmentId" class="form-select mb-3"
                                        aria-label=".form-select example">
                                    <%for (Department d : departmentList) {%>
                                    <option value="<%=d.getDepartmentId()%>"><%=d.getName()%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>

                            <div class="mb-1 py-2">
                                <label for="positionId">Позиція</label>
                                <select id="positionId" name="positionId" class="form-select mb-3"
                                        aria-label=".form-select example">
                                    <%for (Position p : positionList) {%>
                                    <option value="<%=p.getPositionId()%>"><%=p.getName()%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>

                            <div class="mb-1 py-2">
                                <label for="scientificTitleId">Наукове звання</label>
                                <select id="scientificTitleId" name="scientificTitleId" class="form-select mb-3"
                                        aria-label=".form-select example">
                                    <%for (ScientificTitle s : scientificTitles) {%>
                                    <option value="<%=s.getScientificTitleId()%>"><%=s.getName()%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>
                            <div class="mb-1 py-2">
                                <label for="degreeId">Вчений ступінь</label>
                                <select id="degreeId" name="degreeId" class="form-select mb-3"
                                        aria-label=".form-select example">
                                    <%for (Degree d : degreeList) {%>
                                    <option value="<%=d.getDegreeId()%>"><%=d.getName()%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>

                            <div class="d-flex justify-content-center">
                                <button type="submit"
                                        class="btn btn-success btn-lg" style="background-color: rgb(12,140,203)">
                                    Надіслати запит
                                </button>
                            </div>
                            <p class="text-center text-muted mt-5 mb-0">Вже зареєстровані? <a href="/login"
                                                                                              class="fw-bold text-body"><u>Вхід
                                до системи</u></a></p>
                        </form:form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
