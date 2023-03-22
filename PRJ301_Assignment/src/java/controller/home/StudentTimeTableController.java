/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.home;

import controller.auth.BaseAuthenticationController;
import dal.SessionDBContext;
import dal.StudentDBContext;
import dal.TimeSlotDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Account;
import model.Session;
import model.Student;
import model.TimeSlot;
import util.DateTimeHelper;

/**
 *
 * @author PC
 */
public class StudentTimeTableController extends BaseAuthenticationController{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int stdid = Integer.parseInt(req.getParameter("stdid"));
        String raw_from = req.getParameter("from");
        String raw_to = req.getParameter("to");
        java.sql.Date from = null;
        java.sql.Date to = null;
        if(raw_from == null || raw_from.length() == 0 )
        {
            java.util.Date today = new java.util.Date();
            int todayOfWeek = DateTimeHelper.getDayofWeek(today);
            java.util.Date e_from = DateTimeHelper.addDays(today, 2 - todayOfWeek);
            java.util.Date e_to = DateTimeHelper.addDays(today, 8-todayOfWeek);
            from = DateTimeHelper.toDateSql(e_from);
            to = DateTimeHelper.toDateSql(e_to);
            
            
        }
         else
        {
            from = java.sql.Date.valueOf(raw_from);
            to = java.sql.Date.valueOf(raw_to);
        }
         req.setAttribute("from", from);
        req.setAttribute("to", to);
        req.setAttribute("dates", DateTimeHelper.getDateList(from, to));
        
        TimeSlotDBContext slotDB = new TimeSlotDBContext();
        ArrayList<TimeSlot> slots = slotDB.list();
        req.setAttribute("slots", slots);
        
        SessionDBContext sesDB = new SessionDBContext();
        ArrayList<Session> list = sesDB.filterStudent(stdid, from, to);
        req.setAttribute("list", list);
        
        StudentDBContext stdDB = new StudentDBContext();
        Student student = stdDB.get(stdid);
        req.setAttribute("student", student);
        
        req.getRequestDispatcher("view/student/studentTimetable.jsp").forward(req, resp);


    }
    
    
}
