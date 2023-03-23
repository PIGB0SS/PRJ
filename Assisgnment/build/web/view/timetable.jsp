
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class="util.DateTimeHelper"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Time Table</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    </head>
    <body>
        <h1 style="margin-left: 10px;">FPT University Academic Portal</h1>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
        <div class="input-group mb-3" style="margin-left: 10px; margin-top: 10px;width: 30%">
            <span class="input-group-text" id="inputGroup-sizing-default">Lecturer</span>
            <input type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly="readonly" value="${requestScope.lecturer.name}">
        </div>
        <form action="timetable" method="get">
            <!--            <div class="form-floating" style="width: 20%; margin-left: 10px">
                            <select class="form-select" id="floatingSelectGrid" name="lid">
                                <option value="1">Sonnt5</option>
                                <option value="2">Lecture 2</option>
                                <option value="3">Lecture 3</option>
                            </select>
                            <label for="floatingSelectGrid">Lecture</label>
                        </div>-->
            <input type="hidden" name="lid" value="${param.lid}"/>
            <div class="input-group" style="margin-left: 10px; margin-top: 10px;width: 30%">
                <input type="date" name="from" value="${requestScope.from}" class="form-control">
                <input type="date" name="to" value="${requestScope.to}" class="form-control">
                <input type="submit" class="btn btn-outline-secondary" id="button-addon2" value="View"/>
            </div>
        </form>
        <table class="table" style="margin-left: 10px; margin-right: 10px; margin-top: 20px; width: 99%;">
            <thead>
                <tr>

                    <th scope="col">Slot</th>
                    <th scope="col">Time</th>
                        <c:forEach items="${requestScope.dates}" var="d">
                        <th>${d}<br/>${helper.getDayNameofWeek(d)}</th>
                        </c:forEach>
                </tr>
            </thead>
            <tbody>

                <c:forEach items="${requestScope.slots}" var="slot" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td>${slot.des}</td>
                        <c:forEach items="${requestScope.dates}" var="d">
                            <td>
                                <c:forEach items="${requestScope.sessions}" var="ses">
                                    <c:if test="${helper.compare(ses.date,d) eq 0 and (ses.slot.id eq slot.id)}">
                                        ${ses.group.name}-${ses.group.sub.name}<br>
                                        ${ses.room.name}
                                        <c:if test="${ses.attanded}">
                                            <a href="takeatt?id=${ses.id}"><p style="color: blue">Take</p></a>
                                        </c:if>
                                        <c:if test="${!ses.attanded}">
                                            <a href="takeatt?id=${ses.id}"><p style="color: red">Edit</p></a>                          
                                        </c:if>
                                    </c:if>

                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
