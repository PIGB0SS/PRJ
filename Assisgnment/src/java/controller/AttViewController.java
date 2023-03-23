/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ViewDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attendance;
import model.Group;
import model.Session;
import model.Student;
import model.TimeSlot;

public class AttViewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int stdid = Integer.parseInt(request.getParameter("stdid"));
        int id = (int) request.getSession().getAttribute("stdid");
        if (id != stdid) {
            response.getWriter().println("Access denied!");
        } else {
            ViewDBContext viewDB = new ViewDBContext();
            Student student = viewDB.get(stdid);
            ArrayList<Group> listGroup = viewDB.listGroup(stdid);
            ArrayList<Attendance> listAttendance = viewDB.listAttendance(stdid);
            ArrayList<Session> listSession = viewDB.listSession(stdid);
            ArrayList<Attendance> atts = new ArrayList<>();
            for (Session session : listSession) {
                Attendance att = viewDB.getAttendance(stdid, session.getId());
                atts.add(att);
            }
            request.setAttribute("attendances", atts);
            ArrayList<TimeSlot> listTimeSlot = viewDB.listTimeSlot(stdid);
            request.setAttribute("student", student);
            request.setAttribute("groups", listGroup);
            request.setAttribute("atts", listAttendance);
            request.setAttribute("ses", listSession);
            request.setAttribute("tes", listTimeSlot);
            
            request.getRequestDispatcher("../view/view.jsp").forward(request, response);
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
