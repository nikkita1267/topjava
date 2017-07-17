<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
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
    <a href="index.html">Home</a>
    <table>
        <tr class="top">
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
</body>
</html>
