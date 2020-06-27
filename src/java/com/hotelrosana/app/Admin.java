package com.hotelrosana.app;

import com.hotelrosana.util.Passwords;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;

/**
 * 
 * Class for Admin-related functions servlets.
 */
public class Admin extends App {
    protected Passwords passwords;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        super.doGet(request, response);
        
        // check for database connection
        if (null == dbCon) {
            App.showMessage(request, response, "Database Connection Error", "There was an error establishing connection to database.");
            return;
        }
        
        passwords = new Passwords(this.getServletContext().getRealPath("/WEB-INF/passwords.txt"));
    }
}
