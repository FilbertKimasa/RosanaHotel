package com.hotelrosana.app;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login", "/login/" })
public class Login extends Admin {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
         
        String username = null != request.getParameter("username") ? (String) App.unescapeHTML(request.getParameter("username")): "";
        String password = null != request.getParameter("password") ? (String) App.unescapeHTML(request.getParameter("password")): "";
        
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (passwords.find(username, password)) {
                request.getSession().setAttribute("logged_in", username);
                response.sendRedirect(request.getContextPath() + File.separator + "admin/");
                return;
            } else {
                flashMessage("Incorrect username-password combination.");
            }
        }
        
        request.setAttribute("username", App.escapeHTML(username));
        request.setAttribute("password", App.escapeHTML(password));
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
