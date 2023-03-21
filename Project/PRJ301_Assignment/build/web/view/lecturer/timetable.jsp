<%-- 
    Document   : timetable
    Created on : Oct 15, 2022, 9:30:31 AM
    Author     : Ngo Tung Son
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class="util.DateTimeHelper"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
        .container {
            width: 750px;
            padding-right: 15px;
            padding-left: 15px;
            margin-right: auto;
            margin-left: auto;
            background-color: red;
        }
    </style>
    </head>
    
    <body div="container" style="text-align: center;padding-top: 50px">
        
        <p>Lecturer: <input type="text" readonly="readonly" value="${requestScope.lecturer.name}"/ style="text-align:center "></p>
        <p><form action="timetable" method="GET">
            <input type="hidden" name="lid" value="${param.lid}"/>
            From: <input type="date" name="from" value="${requestScope.from}"/>
            To: <input type="date" name="to" value="${requestScope.to}"/>
            <input type="submit" value="View"/> 
        </form></p>
            <table border="1px" style="text-align: center;
                   width:100% ; height:500px ; background-color: lightcyan" >
            <tr>
                <td align="center"> </td>
                <c:forEach items="${requestScope.dates}" var="d">
                    <td style="background-color: #007bff">${d}<br/>${helper.getDayNameofWeek(d)}</td>
                    </c:forEach>
            </tr>
            <c:forEach items="${requestScope.slots}" var="slot">
                <tr>
                    <td align="center" >${slot.description}</td>
                    <c:forEach items="${requestScope.dates}" var="d">
                        <td align="center" >
                            <c:forEach items="${requestScope.sessions}" var="ses">
                                <c:if test="${helper.compare(ses.date,d) eq 0 and (ses.timeslot.id eq slot.id)}">
                                    <a href="takeatt?id=${ses.id}">${ses.group.name}-${ses.group.subject.name}</a>
                                    <br/>
                                    ${ses.room.name}
                                    <c:if test="${ses.attandated}">
                                        <img src="../img/male-icon.png" alt=""/>
                                    </c:if>
                                    <c:if test="${!ses.attandated}">
                                        <img src="../img/female-icon.png" alt=""/>
                                    </c:if>
                                </c:if>

                            </c:forEach>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
