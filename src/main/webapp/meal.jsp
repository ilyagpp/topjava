
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meal</title>
</head>
<style>
    table {
        border-collapse: collapse;
        text-align: center;
    }
    th, td {
        padding: 10px 15px;
        vertical-align: middle;
    }
    #m{
        color: red;
    }

</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal List</h2>
<a href="meals/add">Add Meal</a>
<br>
<br>
<table border="3">
    <tbody>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${meals}">
        <c:if test="${mealTo.excess}"><tr style="color: red"> </c:if>
        <c:if test="${!mealTo.excess}"><tr style="color: green"></c:if>
        <th>${mealTo.dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}</th>
        <th>${mealTo.description}</th>
        <th>${mealTo.calories}</th>
        <th><a href="meals?id=${mealTo.id}">Edit</a> </th>
        <th><form method="post">
            <input hidden name="deleteId" value="${mealTo.id}"/>
            <button type="submit">Delete</button></form>
        </th>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
