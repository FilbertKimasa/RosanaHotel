package com.hotelrosana.app;

import com.hotelrosana.models.Booking;
import com.hotelrosana.models.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AssignRoomServlet", urlPatterns = { "/assign-room", "/assign-room/" })
public class AssignRoom extends Admin {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        String username = (String) request.getSession().getAttribute("logged_in");
        if (null == username) {
            response.sendRedirect(request.getContextPath() + File.separator + "login/");
            return;
        }
        
        String roomNo = null != request.getParameter("room_no") ? App.unescapeHTML(request.getParameter("room_no")): "";
        String bookingID = request.getParameter("bid");
        Booking booking = new Booking(bookingID);
        if (booking.find()) {
            String roomsType = booking.getData("rooms_type");
            ArrayList<String> assignedRooms = booking.getAssignedRooms();
            ArrayList<String> allRooms = Room.getAllRooms(roomsType);
            ArrayList<String> occupiedRooms = Room.getAllOccupiedRooms(roomsType);
            ArrayList<String> availableRooms = new ArrayList<>();
            
            if (null != allRooms && null != occupiedRooms) {
                allRooms.stream().filter((_roomNo) -> (!occupiedRooms.contains(_roomNo))).forEachOrdered((_roomNo) -> {
                    availableRooms.add(_roomNo);
                });
            }
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                if (roomNo.trim().isEmpty()) {
                    flashMessage("Please choose a room.");
                } else {
                    if (null != assignedRooms && assignedRooms.size() >= Integer.parseInt(booking.getData("num_rooms"))) {
                        flashMessage("The number of rooms requested by this booking is already reached.");
                    } else {
                        Room room = new Room(roomNo);
                        if (!room.find() || !availableRooms.contains(roomNo) || !room.getData("type").equals(roomsType)) {
                            flashMessage("Room does not exist or it is not valid here.");
                        } else {
                            if (booking.assignRoom(roomNo)) {
                                flashMessage("Room assigned successfully.");
                                booking.update("seen", "1"); // mark this booking as read implicitly
                            } else {
                                flashMessage("Something went wrong. The room could not be assigned.");
                            }
                        }
                    }
                }
            }
            request.setAttribute("roomNo", App.escapeHTML(roomNo));
            request.setAttribute("availableRooms", availableRooms);
            request.setAttribute("booking", booking);
            request.setAttribute("flashedMessages", getFlashedMessages());
            request.getRequestDispatcher("/WEB-INF/assign-room.jsp").forward(request, response);
        } else {
            App.showMessage(request, response, "Access Denied", "You sent an invalid request.");
        }
    }
}
