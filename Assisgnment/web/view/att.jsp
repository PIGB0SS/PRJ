
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attendance taking</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    </head>
    <body>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
        <h1 style="margin-left: 10px; margin-right: 10px;">Single activity Attendance</h1>
        <h5 style="margin-left: 10px; margin-right: 10px;">Attendance for ${requestScope.ses.group.sub.name} with ${requestScope.ses.lec.name} at ${requestScope.ses.slot.des} on ${requestScope.ses.date} FALL 2022, in room ${requestScope.ses.room.name} at FU_HL</h5>

        <form action="takeatt" method="post">
            <input type="hidden" name="sesid" value="${param.id}"/>
            <table class="table" style="margin-left: 10px; margin-right: 10px; margin-top: 20px; width: 99%;">
                <thead>
                    <tr>
                        <th scope="col">NO</th>
                        <th scope="col">CODE</th>
                        <th scope="col">NAME</th>
                        <th scope="col">STATUS</th>
                        <th scope="col">COMMENT</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.ses.atts}" var="a" varStatus="loop">
                        <tr>
                            <th scope="row">${loop.index + 1}</th>
                            <td>${a.student.id}</td>
                            <input type="hidden" name="stdid" value="${a.student.id}"/>
                            <td>${a.student.name}</td>
                    <td>
                        <input type="radio"
                               <c:if test="${a.present}">
                               checked="checked"
                               </c:if>
                               name="present${a.student.id}" value="present" />
                        <label class="form-check-label" for="flexRadioDefault1">Attend</label>
                        <input type="radio"
                               <c:if test="${!a.present}">
                               checked="checked"
                               </c:if>
                               name="present${a.student.id}" value="absent" />
                        <label class="form-check-label" for="flexRadioDefault2">Absent</label>
                    </td>
                    <td><input type="text" name="description${a.student.id}" value="${a.des}"></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <input type="submit" value="Save" class="btn btn-primary me-md-2"/>
            </div>
        </form>
    </body>
</html>
