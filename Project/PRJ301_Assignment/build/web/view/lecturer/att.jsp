<%-- 
    Document   : att
    Created on : Oct 16, 2022, 3:08:10 PM
    Author     : sonnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vie">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .container{
                width: 750px;
                padding-right: 15px;
                padding-left: 15px;
                margin-right: auto;
                margin-left: auto;

            }
            #table-head {
                background-color: lightblue;
            }
            table{
                width:100%;
                height:auto;
                border:1px #212529
                
            }
            td{
                height: 100px;
            }
            #table-head td{
                height: 50px;
            }
        </style>    

    </head>
    <body>
        <div class="container">
            <h1>Single activity Attendance</h1>
            Take attendance for class: ${requestScope.ses.group.name} at Slot: ${requestScope.ses.timeslot.description} <br/>
            Subject: ${requestScope.ses.group.subject.name} <br/>
            Room: ${requestScope.ses.room.name} <br/>
            Date: ${requestScope.ses.date}<br/>
            Attended: <span style="color: red;"> ${requestScope.ses.attandated?"Yes":"No"} </span>
            <form action="takeatt" method="POST">
                <input type="hidden" name="sesid" value="${param.id}"/>
                <table >
                    <thead id ="table-head">
                        <tr>
                            <td align="center">No.</td>
                            <td align="center">StudentID</td>
                            <td align="center">Full Name</td>
                            <td align="center">Present</td>
                            <td align="center">Absent</td>
                            <td align="center">Description</td>
                        </tr>
                    </thead>
                    <c:forEach items="${requestScope.ses.attandances}" var="a" varStatus="loop">
                        <tr>
                            <td>${loop.index+1}</td>
                            <td>${a.student.id}
                                <input type="hidden" name="stdid" value="${a.student.id}"/>
                            </td>
                            <td>${a.student.name}</td>
                            <td><input type="radio"
                                       <c:if test="${a.present}">
                                           checked="checked"
                                       </c:if>
                                       name="present${a.student.id}" value="present" /></td>
                            <td><input type="radio"
                                       <c:if test="${!a.present}">
                                           checked="checked"
                                       </c:if>
                                       name="present${a.student.id}" value="absent" /></td>
                            <td><input type="text" style="border:0px ;width: 100% ;height: 100%" name="description${a.student.id}" value="${a.description}" /></td>
                        </tr>   

                    </c:forEach>

                </table>
                <input style="height: 40px;
                       float: right; 
                       display: inline-block;
                       width: 100px;
                       border-radius: 2px;" type="submit" value="Save"/>
            </form>
        </div>
    </body>

</html
