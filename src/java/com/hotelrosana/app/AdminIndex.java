package com.hotelrosana.app;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "AdminIndexServlet", urlPatterns = { "/admin", "/admin/" })
public class AdminIndex extends Admin {    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        super.doGet(request, response);
        
        if (null == request.getSession().getAttribute("logged_in")) {
            response.sendRedirect(request.getContextPath() + File.separator + "login/");
            return;
        }
        
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/admin-index.jsp").forward(request, response);
    }
}
