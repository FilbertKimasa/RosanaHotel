package com.hotelrosana.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Room implements Entity {
    private Connection dbCon;
    private String roomNo;
    
    @Override
    public final boolean save(String[] columns, String[] values) {
        ArrayList<String> valueParams = new ArrayList<>();
        for (String i: values) {
            valueParams.add("?");
        }
        
        String query = "INSERT INTO room (" + String.join(", ", columns) + ") VALUES (" + String.join(", ", valueParams) + ")";
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
        if (null != roomNo) {
            String query = "DELETE FROM room WHERE room_no = ?";
            return DBConnection.execute(query, new String[] { roomNo });
        }
        
        return false;
    }
    
    @Override
    public final String getData(String column) {
        if (null == roomNo) return null;
        
        String columnData = null;
        String query = "SELECT " + column + " FROM room WHERE room_no = ?";
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            st.setString(1, roomNo);
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
        if (null == roomNo) return false;
        return DBConnection.execute("UPDATE room SET " + column + " = ? WHERE room_no = ?", new String[] { newValue, roomNo });
    }
    
    @Override
    public final boolean find() {
        if (null == roomNo) return false;
        return DBConnection.getNumRows("SELECT room_no FROM room WHERE room_no = ?", new String[] { roomNo }) > 0;
    }
    
    /**
     * Fetches room and booking data of a column if a room is occupied.
     * 
     * @param column Column whose data is fetched.
     * @return A {@code String} of fetched data on success
     * and {@code null} on failure.
     */
    public final String getBookingData(String column) {
        if (null == roomNo) return null;
        
        String columnData = null;
        if (getData("occupied").equals("1")) {
            String query = "SELECT " + column + " FROM room INNER JOIN room_booking ON room.room_no = room_booking.room_no INNER JOIN booking ON room_booking.booking_id = booking.booking_id INNER JOIN guest ON guest.booking_id = room_booking.booking_id WHERE room.room_no = ?";
            try {
                PreparedStatement st = dbCon.prepareStatement(query);
                st.setString(1, roomNo);
                ResultSet rs = st.executeQuery();
                rs.first();
                return rs.getString(column);
            } catch(SQLException ex) {
                // do nothing
            }
        }
        return columnData;
    }
    
     /**
     * Gets the name of a room in words.
     * 
     * @param roomType A {@code String} representation of an integer
     * that represents a room type.
     * @return Name of a room.
     */
    public static final String getRoomTypeInWords(String roomType) {
        String roomTypeInWords = "";
        if (null != roomType) switch(roomType) {
            case "1":
                roomTypeInWords = "Standard";
                break;
            case "2":
                roomTypeInWords = "Classic";
                break;
            case "3":
                roomTypeInWords = "Superior";
                break;
            case "4":
                roomTypeInWords = "Family Suite";
                break;
        }
        
        return roomTypeInWords;
    }
    
    /**
     * Gets total number of all rooms.
     * 
     * @return Number of rooms.
     */
    public static final int getNumRooms() {        
        return DBConnection.getNumRows("SELECT room_no FROM room WHERE 1", null);
    }
    
    /**
     * Gets total number of all rooms of a given type.
     * 
     * @param roomsType Type of rooms.
     * @return Number of rooms.
     */
    public static final int getNumRooms(String roomsType) {        
        return DBConnection.getNumRows("SELECT room_no FROM room WHERE type = ?", new String[] { roomsType });
    }
    
    /**
     * Gets all rooms.
     * 
     * @return An {@code ArrayList<String>} object of room numbers on 
     * success and {@code null} on failure.
     */
    public static final ArrayList<String> getAllRooms() {
        ArrayList<String> rooms = new ArrayList<>();
        String query = "SELECT room_no FROM room WHERE 1";
        try {
            PreparedStatement st = DBConnection.DB_CON.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) {
                rooms.add(rs.getString("room_no"));
            }
        } catch(SQLException ex) {
            rooms = null;
        }
        
        return rooms;
    }
    
    /**
     * Gets all rooms of a given type.
     * 
     * @param roomsType Type of rooms.
     * @return An {@code ArrayList<String>} object of room numbers on 
     * success and {@code null} on failure.
     */
    public static final ArrayList<String> getAllRooms(String roomsType) {
        ArrayList<String> rooms = new ArrayList<>();
        String query = "SELECT room_no FROM room WHERE type = ?";
        try {
            PreparedStatement st = DBConnection.DB_CON.prepareStatement(query);
            st.setString(1, roomsType);
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) {
                rooms.add(rs.getString("room_no"));
            }
        } catch(SQLException ex) {
            rooms = null;
        }
        
        return rooms;
    }
    
    /**
     * Gets all rooms that are occupied.
     * 
     * @return An {@code ArrayList<String>} object of room numbers on 
     * success and {@code null} on failure.
     */
    public static final ArrayList<String> getAllOccupiedRooms() {
        ArrayList<String> occupiedRooms = new ArrayList<>();
        String query = "SELECT room_no FROM room WHERE occupied = 1";
        try {
            PreparedStatement st = DBConnection.DB_CON.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) {
                occupiedRooms.add(rs.getString("room_no"));
            }
        } catch(SQLException ex) {
            occupiedRooms = null;
        }
        
        return occupiedRooms;
    }
    
    /**
     * Gets all rooms of a given type that are occupied.
     * 
     * @param roomsType Type of rooms.
     * @return An {@code ArrayList<String>} object of room numbers on 
     * success and {@code null} on failure.
     */
    public static final ArrayList<String> getAllOccupiedRooms(String roomsType) {
        ArrayList<String> occupiedRooms = new ArrayList<>();
        String query = "SELECT room_no FROM room WHERE type = ? AND occupied = 1";
        try {
            PreparedStatement st = DBConnection.DB_CON.prepareStatement(query);
            st.setString(1, roomsType);
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) {
                occupiedRooms.add(rs.getString("room_no"));
            }
        } catch(SQLException ex) {
            occupiedRooms = null;
        }
        
        return occupiedRooms;
    }
    
    /**
     * Gets total number of all occupied rooms.
     * 
     * @return Number of rooms.
     */
    public static final int getNumOccupiedRooms() {        
        return DBConnection.getNumRows("SELECT room_no FROM room WHERE occupied = 1", null);
    }
    
    /**
     * Gets total number of all occupied rooms of a given type.
     * 
     * @param roomsType Type of rooms.
     * @return Number of rooms.
     */
    public static final int getNumOccupiedRooms(String roomsType) {        
        return DBConnection.getNumRows("SELECT room_no FROM room WHERE type = ? AND occupied = 1", new String[] { roomsType });
    }
    
    /**
     * Checks whether all rooms are occupied.
     * 
     * @return {@code true} or {@code false}
     */
    public static final boolean allRoomsOccupied() {
        return getNumOccupiedRooms() == getNumRooms();
    }
    
    /**
     * Checks whether all rooms of a given type are occupied.
     * 
     * @param roomsType Type of rooms.
     * @return {@code true} or {@code false}
     */
    public static final boolean allRoomsOccupied(String roomsType) {
        return getNumOccupiedRooms(roomsType) == getNumRooms(roomsType);
    }    
    
    /**
     * This constructor is used when going to save entry data.
     */
    public Room() {
        dbCon = DBConnection.DB_CON;
        roomNo = null;
    }
    
    /**
     * Initializes an object with a given room number.
     * <p>This constructor is specifically used to retrieve
     * data of an already-saved entry and related functions.</p>
     * 
     * @param roomNo The room number.
     */
    public Room(String roomNo) {
        this();
        this.roomNo = roomNo;
    }
}
