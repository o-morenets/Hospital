<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patients</title>
</head>
<body>
    <h1>Patients</h1>

    <c:forEach  var="i" items="${patientsList}">
        <a href="./show_patient_info?id=${i.id}">${i.firstName} ${i.lastName} ${i.surName}</a>
        <br/>
    </c:forEach>

    <br/>
    <a href="./add_patient_form">Add patient</a>
</body>
</html>