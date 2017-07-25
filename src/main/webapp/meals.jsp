<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        div {
            margin-left: 10px;
            margin-right: 10px;
        }

        .from {
            float: left;
            border-right: 1px solid black;
        }

        .to {
            float: left;
        }

        .after {
            clear: both;
        }

        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }

        td {
            text-align: center;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h3>Filter</h3>
    <form id="filter" action="meals" method="post">
        <div class="from">
            <div>
                <label for="sort_date_from">От даты</label>
                <input id="sort_date_from" name="date_from" type="date">
            </div>
            <div>
                <label for="sort_date_to">До даты</label>
                <input id="sort_date_to" name="date_to" type="date">
            </div>
        </div>
        <div class="to">
            <div>
                <label for="sort_time_from">От времени</label>
                <input id="sort_time_from" name="time_from" type="time">
            </div>
            <div>
                <label for="sort_time_to">До времени</label>
                <input id="sort_time_to" name="time_to" type="time">
            </div>
        </div>
        <div class="after">
            <input value="true" name="is_filter" hidden>
            <input type="submit" value="Filter">
        </div>
    </form>
    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>