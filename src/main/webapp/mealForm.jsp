
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 17.02.2023
  Time: 8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<style>

</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="post">

    <div class="container">
        <label for="dateTime"> DateTime
        </label>
        <input id="dateTime" type="datetime-local" name="dateTime" value="${meal.dateTime}">
    </div>
    <br>
    <div>
        <label for="description"> Description
        </label>
        <input id="description" type="text" name="description" value="${meal.description}">
    </div>
    <br>
    <div>
        <label for="calories"> Calories
        </label>
        <input id="calories" type="text" name="calories" max="5" value="${meal.calories}">
    </div>
    <br>
    <div>
        <input hidden name="mealId" value="">
        <button onclick="window.history.back()" type="button">Cancel</button>
        <button name="saveMeal" type="submit">Save</button>
    </div>
</form>
</body>
</html>
