package com.hotelrosana.app;

import com.hotelrosana.models.Booking;
import com.hotelrosana.models.Room;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DoActionServlet", urlPatterns = { "/do-action", "/do-action/" })
public class DoAction extends Admin {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        String username = (String) request.getSession().getAttribute("logged_in");
        if (null == username) {
            response.sendRedirect(request.getContextPath() + File.separator + "login/");
            return;
        }
        
        String action = request.getParameter("a");
        String entry = request.getParameter("ent");
        String bookingID = request.getParameter("bid");
        String roomNo = request.getParameter("room-no");
        if (null != action) {
            if (null != entry) {
                if (entry.equalsIgnoreCase("booking")) {
                    Booking booking = new Booking(bookingID);
                    if (booking.find()) {
                        if (action.equalsIgnoreCase("delete")) {
                            String confirm = request.getParameter("confirm");
                            if (null != confirm && confirm.equals("1") ) {
                                if (booking.delete()) {
                                    App.showMessage(request, response, "Action Successful", "Booking deleted successfully.");
                                    return;
                                }
                            }

                            App.showDialog(request, response, "Confirm Delete", "Are you sure you want to delete this booking?", "do-action/?" + request.getQueryString() + "&amp;confirm=1");
                            return;
                        } else if (action.equalsIgnoreCase("mark-read")) {
                            if (booking.update("seen", "1")) {
                                App.showMessageWithBackLink(request, response, "Info", "Action successful.");
                                return;
                            }
                        }
                    }
                } else if (entry.equalsIgnoreCase("room")) {
                    Room room = new Room(roomNo);
                    if (room.find()) {
                        if (action.equalsIgnoreCase("delete")) {
                            String confirm = request.getParameter("confirm");
                            if (null != confirm && confirm.equals("1") ) {
                                if (room.delete()) {
                                    App.showMessage(request, response, "Action Successful", "Room deleted successfully.");
                                    return;
                                }
                            }

                            App.showDialog(request, response, "Confirm Delete", "Are you sure you want to delete this room?", "do-action/?" + request.getQueryString() + "&amp;confirm=1");
                            return;
                        }
                    }
                }
            } else {
                // do nothing
            }
        }
        
        App.showMessage(request, response, "Action Unsuccessful", "There was an error saving changes or you sent an invalid request.");
    }
}
