package com.hotelrosana.app;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = { "/change-password", "/change-password/" })
public class ChangePassword extends Admin {
       
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        String username = (String) request.getSession().getAttribute("logged_in");
        if (null == username) {
            response.sendRedirect(request.getContextPath() + File.separator + "login/");
            return;
        }
        
        String curPass = null != request.getParameter("cur_pass") ? App.unescapeHTML(request.getParameter("cur_pass")): "";
        String newPass = null != request.getParameter("new_pass") ? App.unescapeHTML(request.getParameter("new_pass")): "";
        String confPass = null != request.getParameter("conf_pass") ? App.unescapeHTML(request.getParameter("conf_pass")): "";
        
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (!passwords.find(username, curPass)) {
                flashMessage("Incorrect current password.");    
            } else {
                if (!newPass.equals(confPass)) {
                    flashMessage("The passwords do not match.");
                } else {
                    // delete user and re-save them
                    passwords.remove(username);
                    try {
                        if (passwords.add(username, newPass)) {
                            flashMessage("Your password was changed.");
                        } else {
                            flashMessage("Something went wrong. Your password could not be changed.");
                        }
                    } catch(Exception ex) {
                        flashMessage("Something went wrong. Your password could not be changed.");
                    }
                }
            }
        }
        
        request.setAttribute("cur_pass", App.escapeHTML(curPass));
        request.setAttribute("new_pass", App.escapeHTML(newPass));
        request.setAttribute("conf_pass", App.escapeHTML(confPass));
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/change-password.jsp").forward(request, response);
    }
}