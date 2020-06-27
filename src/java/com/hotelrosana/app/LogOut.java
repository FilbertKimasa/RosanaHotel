package com.hotelrosana.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LogoutServlet", urlPatterns = { "/logout", "/logout/" })
public class LogOut extends App {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        String referer = null != request.getHeader("referer") ? request.getHeader("referer"): request.getContextPath();
        // remove user in session and redirect to referer
        if (null != request.getSession().getAttribute("logged_in")) {
            request.getSession().removeAttribute("logged_in");
        }
        
        response.sendRedirect(referer);
    }
}