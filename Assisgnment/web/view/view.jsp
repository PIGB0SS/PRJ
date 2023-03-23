

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Attendance View</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    </head>
    <body>
            <h1 style="margin-left: 10px;">Attendance View</h1>
        <h3 style="margin-left: 10px;">Báo cáo điểm danh cho học sinh ${requestScope.student.name}</h3>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

        <table class="table" style="margin-left: 10px; margin-right: 10px; margin-top: 20px; width: 99%;">
            <thead>
                <tr>
                    <th scope="col">NO</th>
                    <th scope="col">DATE</th>
                    <th scope="col">Slot</th>
                    <th scope="col">Group Name</th>
                    <th scope="col">Attendance Status</th>
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.ses}" var="ses" varStatus="loop">
                <tr>
                    <th scope="row">${loop.index + 1}</th>
                    <td>${ses.date}</td>
                    <td>${ses.slot.id}</td>
                    <td>${ses.group.name}</td>
                    
                        <td>
                            <c:forEach items="${requestScope.attendances}" var="a">
                                <c:if test="${(ses.id) eq (a.ses.id)}">
                                    <c:if test="${a.present}"><p style="color: green">Present</p></c:if>
                                    <c:if test="${!a.present}"><p style="color: red">Absent</p></c:if>
                                </c:if>
                            </c:forEach>
                        </td>
                    
                </tr>
                </c:forEach>
                

            </tbody>
        </table>
        <h6 style="margin-left: 10px;">More info/Chú tích thêm</h6>
        <ul class="list-group list-group-flush" style="margin-left: 30px; width: fit-content;">
            <li class="list-group-item">(attended): Student-code had attended this activity / ${requestScope.student.name} đã tham gia hoạt động này</li>
            <li class="list-group-item">(absent): Student-code had NOT attended this activity / ${requestScope.student.name} đã vắng mặt buổi này</li>
            <li class="list-group-item">(-): no data was given / chưa có dữ liệu</li>
        </ul>
    </body>
</html>
