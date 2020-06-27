package com.hotelrosana.models;

import com.hotelrosana.util.TimeCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Booking implements Entity {
    private Connection dbCon;
    private String bookingID;
    
    @Override
    public final boolean save(String[] columns, String[] values) {
        ArrayList<String> valueParams = new ArrayList<>();
        for (String i: values) {
            valueParams.add("?");
        }
        
        String query = "INSERT INTO booking (" + String.join(", ", columns) + ") VALUES (" + String.join(", ", valueParams) + ")";
        return DBConnection.execute(query, values);
    }
    
    @Override
    public final boolean save(String table, String[] columns, String[] values) {
        ArrayList<String> valueParams = new ArrayList<>();
        for (String i: values) {
            valueParams.add("?");
        }
        
        String query = "INSERT INTO " + table + " (" + String.join(", ", columns) + ") VALUES (" + String.join(", ", valueParams) + ")";
        return DBConnection.execute(query, values);
    }
    
    @Override
    public final boolean delete() {
        if (null != bookingID) {
            String query = "DELETE FROM booking WHERE booking_id = ?";
            return deleteAssignedRooms() && DBConnection.execute(query, new String[] { bookingID });
        }
        
        return false;
    }
    
    @Override
    public final String getData(String column) {
        if (null == bookingID) return null;
        
        String columnData = null;
        String query = "SELECT " + column + " FROM booking LEFT JOIN guest ON booking.booking_id = guest.booking_id LEFT JOIN payment ON booking.booking_id = payment.booking_id WHERE booking.booking_id = ?";
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            st.setString(1, bookingID);
            ResultSet rs = st.executeQuery();
            rs.first();
            return rs.getString(column);
        } catch(SQLException ex) {
            // do nothing
        }
        return columnData;
    }
    
    @Override
    public final boolean update(String column, String newValue) {
        if (null == bookingID) return false;
        return DBConnection.execute("UPDATE booking SET " + column + " = ? WHERE booking_id = ?", new String[] { newValue, bookingID });
    }
    
    @Override
    public final boolean find() {
        if (null == bookingID) return false;
        return DBConnection.getNumRows("SELECT booking_id FROM booking WHERE booking_id = ?", new String[] { bookingID }) > 0;
    }
    
    /**
     * Checks whether a booking is marked as read or not.
     * @return {@code true} or {@code false}.
     */
    public final boolean isRead() {
        return Integer.parseInt(getData("seen")) == 1;
    }
    
    /**
     * Gets time when a booking was ordered in counts
     * like seconds, days, weeks.
     * 
     * @return Time counts as a string on success and 
     * {@code null} on failure.
     */
    public final String getTimeCount() {
        try {
           return new TimeCount((new Date().getTime() / 1000) - (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getData("booking_datetime")).getTime() / 1000)).getTime(); 
        } catch(ParseException ex) {
            return null;
        }
    }
    
    /**
     * Assigns a room to a given booking ID.
     * 
     * @param roomNo Room number to be assigned.
     * @return {@code true} on success and {@code false} on failure.
     */
    public final boolean assignRoom(String roomNo) {
        if (null == bookingID) return false;
        return  DBConnection.execute("INSERT INTO room_booking(room_no, booking_id) VALUES(?, ?)", new String[] { roomNo, bookingID })
                &&
                DBConnection.execute("UPDATE room SET occupied = 1 WHERE room_no = ?", new String[] { roomNo });
    }
    
    /**
     * Deletes all assigned rooms to a given booking ID.
     * 
     * @return {@code true} on success and {@code false} on failure.
     */
    public final boolean deleteAssignedRooms() {
        for (String roomNo: getAssignedRooms()) {
            DBConnection.execute("UPDATE room SET occupied = 0 WHERE room_no = ?", new String[] { roomNo });
        }
        return DBConnection.execute("DELETE FROM room_booking WHERE booking_id = ?", new String[] { bookingID });
    }
    
    /**
     * Checks whether there are any assigned rooms to a given booking ID.
     * 
     * @return {@code true} on success and {@code false} on failure.
     */
    public final boolean hasAssignedRoom() {
        int rowCount = DBConnection.getNumRows("SELECT room_no FROM room_booking WHERE booking_id = ?", new String[] { bookingID });
        return rowCount > 0;
    }
    
    /**
     * Gets all assigned rooms to a given booking ID.
     * 
     * @return An {@code ArrayList<String>} object of room numbers on 
     * success and {@code null} on failure.
     */
    public ArrayList<String> getAssignedRooms() {
        if (null == bookingID) return null;
        
        ArrayList<String> assignedRooms = new ArrayList<>();
        String query = "SELECT room_no FROM room_booking WHERE booking_id = ?";
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            st.setString(1, bookingID);
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) {
                assignedRooms.add(rs.getString("room_no"));
            }
        } catch(SQLException ex) {
            assignedRooms = null;
        }
        
        return assignedRooms;
    }
    
    /**
     * This constructor is used when going to save entry data.
     */
    public Booking() {
        dbCon = DBConnection.DB_CON;
        bookingID = null;
    }
    
    /**
     * Initializes an object with a given booking ID.
     * <p>This constructor is specifically used to retrieve
     * data of an already-saved entry and related functions.</p>
     * 
     * @param bookingID The booking ID.
     */
    public Booking(String bookingID) {
        this();
        this.bookingID = bookingID;
    }
}
