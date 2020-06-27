package com.hotelrosana.app;

import com.hotelrosana.models.DBConnection;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Class with utility functions for other servlets.
 */
public class App extends HttpServlet {
    protected Connection dbCon;
    private ArrayList<String> flashedMessages;
    
    @Override
    public void init() throws ServletException {
        flashedMessages = null;
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        // load database connection
        dbCon = DBConnection.dbConnect();
        DBConnection.DB_CON = dbCon;
        
        // clear flashed messages if present
        if (null != flashedMessages) {
            flashedMessages.clear();
        } else {
            flashedMessages = new ArrayList<>();
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    @Override
    public void destroy() {
        DBConnection.dbClose();
    }
    
    public static final String escapeHTML(String string) {
        if (null != string) {
            string = string.replace("&", "&amp;");
            string = string.replace("<", "&lt;");
            string = string.replace(">", "&gt;");
            string = string.replace("\"", "&quot;");
            string = string.replace("'", "&apos;");
        }
        return string;
    }
    
    public static final String unescapeHTML(String string) {
        if (null != string) {
            string = string.replace("&amp;", "&");
            string = string.replace("&lt;", "<");
            string = string.replace("&gt;", ">");
            string = string.replace("&quot;", "\"");
            string = string.replace("&apos;", "'");
        }
        return string;
    }
    
    protected final void flashMessage(String message) {
        if (null != flashedMessages) {
            flashedMessages.add(message);
        }
    }
    
    protected final ArrayList<String> getFlashedMessages() {
        return flashedMessages;
    }
    
    /**
     * @return Date string in the form {@code yyyy-MM-dd HH:mm:ss}
     */
    public static final String getCurrentDate() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }
    
    /**
     * Generates a random string.
     * 
     * @param length Length of a string to be generated.
     * @return Generated {@code String }
     */
    public static final String randomString(int length) {
        String string = "";
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int randInt = (new Random()).nextInt(62);
            string += chars.charAt(randInt);
        }
        return string;
    }
    
    public static final void showMessage(HttpServletRequest request, HttpServletResponse response, String heading, String message) throws ServletException, IOException {
        request.setAttribute("heading", heading);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
    }
    
    public static final void showMessageWithBackLink(HttpServletRequest request, HttpServletResponse response, String heading, String message) throws ServletException, IOException {
        request.setAttribute("heading", heading);
        request.setAttribute("message", message);
        request.setAttribute("with_back_link", true);
        request.setAttribute("back_link", request.getHeader("referer"));
        request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
    }
    
    public static final void showMessageWithBackLink(HttpServletRequest request, HttpServletResponse response, String heading, String message, String backLink, String backLinkLabel) throws ServletException, IOException {
        request.setAttribute("heading", heading);
        request.setAttribute("message", message);
        request.setAttribute("with_back_link", true);
        request.setAttribute("back_link", backLink);
        request.setAttribute("back_link_label", backLinkLabel);
        request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
    }
    
    public static final void showDialog(HttpServletRequest request, HttpServletResponse response, String heading, String message, String yesLink) throws ServletException, IOException {
        request.setAttribute("heading", heading);
        request.setAttribute("message", message);
        request.setAttribute("yes_link", yesLink);
        request.setAttribute("no_link", request.getHeader("referer"));
        request.getRequestDispatcher("/WEB-INF/dialog.jsp").forward(request, response);
    }
}
