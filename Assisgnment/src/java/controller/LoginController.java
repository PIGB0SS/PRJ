/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDBContext;
import dal.LecturerDBContext;
import dal.StudentDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Lecture;
import model.Student;

/**
 *
 * @author binhp
 */
public class LoginController extends HttpServlet {

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
        request.getRequestDispatcher("view/login.jsp").forward(request, response);

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountDBContext db = new AccountDBContext();
        Account account = db.get(username, password);

//        response.getWriter().println("dis: " + displayname);
//        if (s != null) {
//            String stdname = s.getName();
//            response.getWriter().println("stdname" + stdname);
//        }
//        if (l != null) {
//            String lname = l.getName();
//            response.getWriter().println("lname: " + lname);
//        }
        if (account != null) {
            request.getSession().setAttribute("account", account);
            LecturerDBContext lecDB = new LecturerDBContext();
            Lecture l = lecDB.get(account.getDisplayname());
            StudentDBContext stDB = new StudentDBContext();
            Student s = stDB.get(account.getDisplayname());
            if (s != null) {
                request.getSession().setAttribute("stdid", s.getId());
                response.sendRedirect("student/attview?stdid=" + s.getId());
            } else if (l != null) {
                request.getSession().setAttribute("lid", l.getId());
                response.sendRedirect("lecture/timetable?lid=" + l.getId());
            }
        } else {
            response.getWriter().println("Login failed!");
        }
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
