package com.hotelrosana.app;

import com.hotelrosana.models.Room;
import com.hotelrosana.util.Pagination;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewServlet", urlPatterns = { "/view", "/view/" })
public class View extends Admin {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        String username = (String) request.getSession().getAttribute("logged_in");
        if (null == username) {
            response.sendRedirect(request.getContextPath() + File.separator + "login/");
            return;
        }
        
        String section = request.getParameter("sec");
        if (null == section || !Arrays.asList("bookings", "rooms").contains(section.toLowerCase())) {
            App.showMessage(request, response, "Invalid Request", "Access denied due to an invalid request.");
            return;
        }
        
        String pageTitle = "", pageURL = "", query = "";
        Pagination pagination = new Pagination();
        pagination.setRequest(request);
        pagination.setConnection(dbCon);
        
        if (section.equalsIgnoreCase("bookings")) {
            String category = request.getParameter("cat");
            if (null == category || !Arrays.asList("all", "read", "unread").contains(category.toLowerCase())) {
                response.sendRedirect(request.getContextPath() + File.separator + "view/?sec=bookings&cat=all");
                return;
            }
            
            pagination.setLimit(1);
            
            if (category.equalsIgnoreCase("all")) {
                pageTitle = "All Bookings";
                pageURL = "view/?sec=bookings&amp;cat=all&amp;";
                query = "SELECT booking_id FROM booking WHERE 1 ORDER BY booking_datetime ASC";
                
            } else if (category.equalsIgnoreCase("read")) {
                pageTitle = "Read Bookings";
                pageURL = "view/?sec=bookings&amp;cat=read&amp;";
                query = "SELECT booking_id FROM booking WHERE seen = 1 ORDER BY booking_datetime ASC";
                
            } else {
                pageTitle = "Unread Bookings";
                pageURL = "view/?sec=bookings&amp;cat=unread&amp;";
                query = "SELECT booking_id FROM booking WHERE seen = 0 ORDER BY booking_datetime ASC";
            }
            
        } else if (section.equalsIgnoreCase("rooms")) {
            pageTitle = "Rooms";
            pageURL = "view/?sec=rooms&amp;";
            pagination.setLimit(10);
            query = "SELECT room_no FROM room WHERE 1";
            
            String roomNo = null != request.getParameter("room_no") ? App.unescapeHTML(request.getParameter("room_no")): "";
            String roomType = null != request.getParameter("room_type") ? App.unescapeHTML(request.getParameter("room_type")): "";
            if (request.getMethod().equalsIgnoreCase("post")) {
                if (roomNo.trim().isEmpty()) {
                    flashMessage("Room Number cannot be blank.");
                } else {
                    if (!Pattern.matches("^[\\w\\-\\s]{1,10}$", roomNo)) {
                       flashMessage("Invalid Room Number. Only word characters, hyphens and spaces are allowed with a maximum length of 10."); 
                    } else {
                        // check if room exists
                        if (new Room(roomNo).find()) {
                            flashMessage("Room Number already exists.");
                        }
                    }
                }
                
                if (roomType.equals("0")) {
                    flashMessage("You did not select Type of Room.");
                }
                
                // check errors and save info to database
                if (getFlashedMessages().size() < 1) {
                    Room room = new Room();
                    if (room.save(new String[] { "room_no", "type" }, new String[] { roomNo, roomType })) {
                        flashMessage("Room saved successfully.");
                    } else {
                        flashMessage("Something went wrong. Room could not be saved.");
                    }
                }
            }
            
            request.setAttribute("room_no", App.escapeHTML(roomNo));
            request.setAttribute("room_type", App.escapeHTML(roomType));
        }
        
        pagination.setRowCount(query, null);
        request.setAttribute("pageURL", pageURL);
        request.setAttribute("pagination", pagination);
        request.setAttribute("pageTitle", App.escapeHTML(pageTitle));
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/view.jsp").forward(request, response);
    }
}
