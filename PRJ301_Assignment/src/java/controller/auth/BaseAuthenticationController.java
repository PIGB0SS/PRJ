/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Account;

/**
 *
 * @author PC
 */
public abstract class BaseAuthenticationController extends HttpServlet{
    
    private boolean isAuthen(HttpServletRequest req){
        return req.getSession().getAttribute("account") != null;
    }

 
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp,Account account) throws ServletException, IOException;
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp,Account account) throws ServletException, IOException ;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAuthen(req)){
            Account account = (Account)req.getAttribute("account");
            doPost(req, resp);
        }
        else{
            resp.getWriter().println("AccessDenined,Please login again");
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if(isAuthen(req)){
           Account account = (Account)req.getAttribute("account");
           doGet(req, resp);
       }
       else{
            resp.getWriter().println("Access Denined ! ,Please login again");
            
        }
    }
    
    
        
    
    
    
}
