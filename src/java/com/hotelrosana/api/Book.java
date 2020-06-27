package com.hotelrosana.api;

import com.hotelrosana.models.Booking;
import com.hotelrosana.models.DBConnection;
import com.hotelrosana.models.Room;
import static com.hotelrosana.app.App.randomString;
import static com.hotelrosana.app.App.getCurrentDate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BookingServletAPI", urlPatterns = { "/api/book", "/api/book/" })
public class Book extends HttpServlet {
    private Connection dbCon;
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // load database connection
        dbCon = DBConnection.dbConnect();
        DBConnection.DB_CON = dbCon;
        
        String message = "";
        String status = "error";
        
        // check for database connection
        if (null == dbCon) {
            message = "Database Connection Error.";
        } else {
            String step = request.getParameter("step");
            if (step.equals("check-availability")) {
                String roomsType = request.getParameter("rooms_type");
                String numRooms = request.getParameter("num_rooms");
                
                if (Room.allRoomsOccupied(roomsType)) {
                    message = "Sorry, all of our rooms of this type are occupied! Thank you.";
                } else {
                    int unoccupiedRooms = Room.getNumRooms(roomsType) - Room.getNumOccupiedRooms(roomsType);
                    if (Integer.parseInt(numRooms) > unoccupiedRooms) {
                        message = "Sorry, we have only " + unoccupiedRooms + " rooms of this type that are not occupied!";
                    } else {
                        status = "success";
                    }
                }

            } else if (step.equals("save")) {
                String roomsType = request.getParameter("rooms_type");
                String numRooms = request.getParameter("num_rooms");
                String arrivalDate = request.getParameter("arrival");
                String departDate = request.getParameter("departure");
                String fullName = request.getParameter("full_name");
                String mobile = request.getParameter("mobile");
                String email = request.getParameter("email");
                String nationality = request.getParameter("nationality");
                String amountPaid = request.getParameter("amount_paid");
                String bank = request.getParameter("bank");
                String refNums = request.getParameter("ref_nums");
                
                // reformat dates
                try {
                    arrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(arrivalDate));
                    departDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(departDate));
                } catch(ParseException ex) {
                    // do nothing
                }
                
                // save all data to database
                Booking booking = new Booking();
                String bookingID = randomString(12);
                
                boolean bookingSaved = booking.save(
                        new String[] { "booking_id", "num_rooms", "rooms_type", "booking_datetime", "arrival", "departure" },
                        new String[] { bookingID, numRooms, roomsType, getCurrentDate(), arrivalDate, departDate });
                
                boolean guestSaved = booking.save(
                        "guest",
                        new String[] { "email", "name", "mobile", "nationality", "booking_id" },
                        new String[] { email, fullName, mobile, nationality, bookingID });
                
                boolean paymentSaved = booking.save(
                        "payment",
                        new String[] { "amount", "bank", "ref_no", "booking_id" },
                        new String[] { amountPaid, bank, refNums, bookingID });
                
                if (bookingSaved && guestSaved && paymentSaved) {
                    message = "Your booking was saved successfully. We shall email you a response as soon as possible.";
                    status = "success";
                } else {
                    message = "Something went wrong. Your booking could not be saved successfully.";
                }
            }
        }
        
        response.setHeader("Content-Type", "application/json");
        PrintWriter out = response.getWriter();
        JsonObject json = Json.createObjectBuilder()
                .add("message", message)
                .add("status", status)
                .build();
        out.println(json.toString());
    }
    
    @Override
    public void destroy() {
        DBConnection.dbClose();
    }
}
