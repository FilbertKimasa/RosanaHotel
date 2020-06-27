package com.hotelrosana.app;

import com.hotelrosana.models.Booking;
import com.hotelrosana.models.Room;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "BookingServlet", urlPatterns = { "/book", "/book/" })
public class Book extends App {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        // check for database connection
        if (null == dbCon) {
            App.showMessage(request, response, "Database Connection Error", "There was an error establishing connection to database.");
            return;
        }
        
        String step = request.getParameter("step");
        HttpSession session = request.getSession();
        
        if (!Arrays.asList("1", "2", "3", "4").contains(step)) {
            response.sendRedirect(request.getContextPath() + File.separator + "book/?step=1");
            return;
        }
        
        if (step.equals("1")) {
            // check whether a user skipped this step and notify them
            if (null != session.getAttribute("skipped_step_message")) {
                flashMessage((String) session.getAttribute("skipped_step_message"));
                session.removeAttribute("skipped_step_message");
            }
            
            // check if data was saved in session and update variables
            String roomsType = null != session.getAttribute("rooms_type") ? (String) session.getAttribute("rooms_type"): "";
            String numRooms = null != session.getAttribute("num_rooms") ? (String) session.getAttribute("num_rooms"): "";
            String arrivalDate = null != session.getAttribute("arrival") ? (String) session.getAttribute("arrival"): "";
            String departDate = null != session.getAttribute("departure") ? (String) session.getAttribute("departure"): "";
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                roomsType = request.getParameter("rooms_type");
                numRooms = request.getParameter("num_rooms");
                arrivalDate = App.unescapeHTML(request.getParameter("arrival"));
                departDate = App.unescapeHTML(request.getParameter("departure"));
                
                // save post data into session
                session.setAttribute("rooms_type", roomsType);
                session.setAttribute("num_rooms", numRooms);
                session.setAttribute("arrival", arrivalDate);
                session.setAttribute("departure", departDate);
                
                // validate inputs
                if (Integer.parseInt(roomsType) < 1) {
                    flashMessage("You did not select Type of Rooms.");
                } else {
                    if (Integer.parseInt(numRooms) < 1) {
                        flashMessage("You did not select Number of Rooms.");
                    } else {
                        if (Room.allRoomsOccupied(roomsType)) {
                            flashMessage("Sorry, all of our rooms of this type are occupied! Thank you.");
                        } else {
                            int unoccupiedRooms = Room.getNumRooms(roomsType) - Room.getNumOccupiedRooms(roomsType);
                            if (Integer.parseInt(numRooms) > unoccupiedRooms) {
                                flashMessage("Sorry, we have only " + unoccupiedRooms + " rooms of this type that are not occupied!");
                            }
                        }
                    }
                }

                if (arrivalDate.trim().isEmpty()) {
                    flashMessage("Date of Arrival is required.");
                } else {
                    try {
                        arrivalDate = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(arrivalDate));
                    } catch(ParseException ex) {
                        flashMessage("Invalid Date of Arrival. Make sure it is in the format of dd-mm-yyyy.");
                    }
                }

                if (departDate.trim().isEmpty()) {
                    flashMessage("Date of Departure is required. Make sure it is in the format of dd-mm-yyyy.");
                } else {
                    try {
                        departDate = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(departDate));
                    } catch(ParseException ex) {
                        flashMessage("Invalid Date of Departure. Make sure it is in the format of dd-mm-yyyy.");
                    }
                }
                
                // check for errors and get info saved in session
                if (getFlashedMessages().size() < 1) {
                    session.setAttribute("step_1_saved", true);
                    response.sendRedirect(request.getContextPath() + File.separator + "book/?step=2");
                    return;
                } else {
                    if (null != session.getAttribute("step_1_saved")) {
                        session.removeAttribute("step_1_saved");
                    }
                }
            }
            
            request.setAttribute("rooms_type", roomsType);
            request.setAttribute("num_rooms", numRooms);
            request.setAttribute("arrival", App.escapeHTML(arrivalDate));
            request.setAttribute("departure", App.escapeHTML(departDate));
        }
        
        if (step.equals("2")) {
            // check whether a user skipped this step and notify them
            if (null != session.getAttribute("skipped_step_message")) {
                flashMessage((String) session.getAttribute("skipped_step_message"));
                session.removeAttribute("skipped_step_message");
            }
            
            // check if data was saved in session and update variables
            String fName = null != session.getAttribute("f_name") ? (String) session.getAttribute("f_name"): "";
            String lName = null != session.getAttribute("l_name") ? (String) session.getAttribute("l_name"): "";
            String email = null != session.getAttribute("email") ? (String) session.getAttribute("email"): "";
            String mobile = null != session.getAttribute("mobile") ? (String) session.getAttribute("mobile"): "";
            String nationality = null != session.getAttribute("nationality") ? (String) session.getAttribute("nationality"): "";
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                fName = App.unescapeHTML(request.getParameter("f_name"));
                lName = App.unescapeHTML(request.getParameter("l_name"));
                email = App.unescapeHTML(request.getParameter("email"));
                mobile = App.unescapeHTML(request.getParameter("mobile"));
                nationality = App.unescapeHTML(request.getParameter("nationality"));
                
                // save post data into session
                session.setAttribute("f_name", fName);
                session.setAttribute("l_name", lName);
                session.setAttribute("email", email);
                session.setAttribute("mobile", mobile);
                session.setAttribute("nationality", nationality);
                
                // validate inputs
                if (fName.trim().isEmpty()) {
                    flashMessage("First Name is required.");
                } else {
                    if (!Pattern.matches("^([a-zA-Z]+[\\.\\s\\-']+)*[a-zA-Z]+$", fName)) {
                        flashMessage("Invalid First Name. Please do not put unnecessary characters.");
                    } else {
                        if (fName.length() > 10) {
                            flashMessage("Too long First Name. Only 10 characters are allowed.");
                        }
                    }
                }

                if (lName.trim().isEmpty()) {
                    flashMessage("Last Name is required.");
                } else {
                    if (!Pattern.matches("^([a-zA-Z]+[\\.\\s\\-']+)*[a-zA-Z]+$", lName)) {
                        flashMessage("Invalid Last Name. Please do not put unnecessary characters.");
                    } else {
                        if (lName.length() > 10) {
                            flashMessage("Too long Last Name. Only 10 characters are allowed.");
                        }
                    }
                }

                if (email.trim().isEmpty()) {
                    flashMessage("Email is required.");
                } else {
                    if (!Pattern.matches("^(\\w+\\.)*\\w+@\\w+(\\.\\w+){1,2}$", email)) {
                        flashMessage("Invalid Email.");
                    } else {
                        if (email.length() > 100) {
                            flashMessage("Too long Email. Only 100 characters are allowed.");
                        }
                    }
                }

                if (mobile.trim().isEmpty()) {
                    flashMessage("Your Mobile phone number is required.");
                } else {
                    if (!Pattern.matches("^[\\-\\s\\d\\+\\(\\)]{1,20}$", mobile)) {
                        flashMessage("Invalid Mobile phone number or the characters exceed 20.");
                    }
                }

                if (nationality.trim().isEmpty()) {
                    flashMessage("Your Nationality is required.");
                } else {
                    if (!Pattern.matches("^[a-zA-Z\\-\\s\\+\\(\\)&\\.',]{1,50}$", nationality)) {
                        flashMessage("Invalid Nationality or the characters exceed 50.");
                    }
                }
                
                // check for errors and get info saved in session
                if (getFlashedMessages().size() < 1) {
                    session.setAttribute("step_2_saved", true);
                    response.sendRedirect(request.getContextPath() + File.separator + "book/?step=3");
                    return;
                } else {
                    if (null != session.getAttribute("step_2_saved")) {
                        session.removeAttribute("step_2_saved");
                    }
                }
            }
            
            request.setAttribute("f_name", App.escapeHTML(fName));
            request.setAttribute("l_name", App.escapeHTML(lName));
            request.setAttribute("email", App.escapeHTML(email));
            request.setAttribute("mobile", App.escapeHTML(mobile));
            request.setAttribute("nationality", App.escapeHTML(nationality));
        }
        
        if (step.equals("3")) {
            // check whether a user skipped this step and notify them
            if (null != session.getAttribute("skipped_step_message")) {
                flashMessage((String) session.getAttribute("skipped_step_message"));
                session.removeAttribute("skipped_step_message");
            }
            
            // check if data was saved in session and update variables
            String amountPaid = null != session.getAttribute("amount_paid") ? (String) session.getAttribute("amount_paid"): "";
            String bank = null != session.getAttribute("bank") ? (String) session.getAttribute("bank"): "";
            String refNums = null != session.getAttribute("ref_nums") ? (String) session.getAttribute("ref_nums"): "";
            
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                amountPaid = App.unescapeHTML(request.getParameter("amount_paid"));
                bank = App.unescapeHTML(request.getParameter("bank"));
                refNums = App.unescapeHTML(request.getParameter("ref_nums"));
                
                // save post data into session
                session.setAttribute("amount_paid", amountPaid);
                session.setAttribute("bank", bank);
                session.setAttribute("ref_nums", refNums);

                // validate inputs            
                if (amountPaid.trim().isEmpty()) {
                    flashMessage("Amount of money that you paid is required.");
                } else {
                    if (!Pattern.matches("^\\d{1,10}$", amountPaid)) {
                        flashMessage("Invalid Amount or the digits exceed 10.");
                    }
                }

                if (bank.trim().isEmpty()) {
                    flashMessage("Bank name is required.");
                } else {
                    if (!Pattern.matches("^[a-zA-Z\\-\\s\\+\\(\\)&\\.',]{1,50}$", bank)) {
                        flashMessage("Invalid Bank name or the characters exceed 50.");
                    }
                }

                if (refNums.trim().isEmpty()) {
                    flashMessage("Reference Numbers is required.");
                } else {
                    if (!Pattern.matches("^[a-zA-Z0-9]{1,20}$", refNums)) {
                        flashMessage("Invalid Reference numbers or the characters exceed 20.");
                    }
                }
                
                // check for errors and get info saved in session
                if (getFlashedMessages().size() < 1) {
                    session.setAttribute("step_3_saved", true);
                    response.sendRedirect(request.getContextPath() + File.separator + "book/?step=4");
                    return;
                } else {
                    if (null != session.getAttribute("step_3_saved")) {
                        session.removeAttribute("step_3_saved");
                    }
                }                
            }
            
            request.setAttribute("amount_paid", App.escapeHTML(amountPaid));
            request.setAttribute("bank", App.escapeHTML(bank));
            request.setAttribute("ref_nums", App.escapeHTML(refNums));
        }
        
        if (step.equals("4")) {
            // check if user has not completed some steps and redirect them back to those steps
            Pattern stepPatt = Pattern.compile("^step_(\\d+)_saved$");
            for (String i: Arrays.asList("step_1_saved", "step_2_saved", "step_3_saved")) {
                if (null == session.getAttribute(i)) {
                    session.setAttribute("skipped_step_message", "You have not completed filling this form properly.");
                    Matcher stepMatcher = stepPatt.matcher(i);
                    stepMatcher.find();
                    response.sendRedirect(request.getContextPath() + File.separator + "book/?step=" + stepMatcher.group(1));
                    return;
                }
            }
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                String arrivalDate = (String) session.getAttribute("arrival");
                String departDate = (String) session.getAttribute("departure");
                
                // reformat dates
                try {
                    arrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(arrivalDate));
                    departDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(departDate));
                } catch(ParseException ex) {
                    // do nothing
                }
                
                // save all data to database
                Booking booking = new Booking();
                String bookingID = App.randomString(12);
                
                boolean bookingSaved = booking.save(
                        new String[] { "booking_id", "num_rooms", "rooms_type", "booking_datetime", "arrival", "departure" },
                        new String[] { bookingID, (String) session.getAttribute("num_rooms"), (String) session.getAttribute("rooms_type"), App.getCurrentDate(), arrivalDate, departDate });
                
                boolean guestSaved = booking.save(
                        "guest",
                        new String[] { "email", "name", "mobile", "nationality", "booking_id" },
                        new String[] { (String) session.getAttribute("email"), (String) session.getAttribute("f_name") + " " + (String) session.getAttribute("l_name"), (String) session.getAttribute("mobile"), (String) session.getAttribute("nationality"), bookingID });
                
                boolean paymentSaved = booking.save(
                        "payment",
                        new String[] { "amount", "bank", "ref_no", "booking_id" },
                        new String[] { (String) session.getAttribute("amount_paid"), (String) session.getAttribute("bank"), (String) session.getAttribute("ref_nums"), bookingID });
                
                if (bookingSaved && guestSaved && paymentSaved) {
                    session.invalidate();
                    App.showMessageWithBackLink(request, response, "Info", "Your booking was saved successfully. We shall email you a response as soon as possible.", "#", "Go to Home");
                    return;
                } else {
                    flashMessage("Something went wrong. Your booking could not be saved successfully.");
                }
            }
        }
        
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/book.jsp").forward(request, response);
    }
}
