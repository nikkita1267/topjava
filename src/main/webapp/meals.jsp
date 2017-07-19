<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            width: 100%;
        }

        tr.exceed {
            background-color: #ff514d;
        }

        tr.not_exceed {
            background-color: #6eff53;
        }
    </style>
</head>
<body>
    <a href="index.html">Home</a><hr>
    <c:set var="action" value="${param.get('action')}" />
    <c:set var="isActionValid" value="${action.equals('add') || action.equals('update') || action.equals('delete')}" />

    <c:if test="${!isActionValid}">
        <div style="display: inline-block">
            <a href="meals.jsp?action=add">Add</a>
            <a href="meals.jsp?action=update">Update</a>
            <a href="meals.jsp?action=delete">Delete</a>
        </div>
    </c:if>

    <c:if test="${isActionValid}">
        <form action="${pageContext.request.contextPath}/meals" method="post">
            <c:if test="${action.equals('update') || action.equals('delete')}">
                <label for="id">Id</label>
                <input id="id" type="number" name="id"><br>
            </c:if>
            <c:if test="${action.equals('add') || action.equals('update')}">
            <label for="desc">Description</label>
            <input id = "desc" type="text" name="description"><br>
            <label for="date_time">Date</label>
            <input id="date_time" type="datetime-local" name="date_time"><br>
            <label for="calories">Calories</label>
            <input id="calories" type="number" name="calories"><br>
            </c:if>
            <input hidden name="method" value="${action}">
            <input type="submit" value="${action}"><br>
        </form>
    </c:if>

    <c:if test="${!isActionValid}">
    <table>
        <tr class="top">
            <th>
                Id
            </th>
            <th>
                Description
            </th>
            <th>
                Date Time
            </th>
            <th>
                Calories
            </th>
        </tr>
        <%--@elvariable id="meals" type="java.util.List"--%>
        <c:forEach items="${meals}" var="meal">
            <c:if test="${meal.exceed}"><tr class="exceed"></c:if>
            <c:if test="${!meal.exceed}"><tr class="not_exceed"></c:if>
                <td>
                    <c:out value="${meal.id}" />
                </td>
                <td>
                    <c:out value="${meal.description}" />
                </td>
                <td>
                        ${f:formatLocalDateTime(meal.dateTime)}
                </td>
                <td>
                    <c:out value="${meal.calories}" />
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</body>
</html>
