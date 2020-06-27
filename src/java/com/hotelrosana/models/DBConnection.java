package com.hotelrosana.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    public static Connection DB_CON = null;
    
    /**
     * Tries to establish a database connection.
     * 
     * @return {@code Connection} object on success and {@code null} on failure.
     */
    public static final Connection dbConnect() {
        Connection dbCon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/rosana_hotel?user=root&password=");
            dbCon.setAutoCommit(false);
        } catch(ClassNotFoundException | SQLException ex) {
            // do nothing
        }
        return dbCon;
    }
    
    /**
     * Tries to close a database connection.
     */
    public static final void dbClose() {
        if (null != DB_CON) {
            try {
                DB_CON.close();
            } catch(SQLException ex) {
                // do nothing
            }
        }
    }
    
    /**
     * Executes a given query and does not know about the results.
     * 
     * @param query A query with unnamed parameters to be executed.
     * @param  queryParamValues Values to be bound to query parameters.
     * @return {@code true} on success and {@code false} on failure.
     */
    public static final boolean execute(String query, String[] queryParamValues) {
        if (null == DB_CON) return false;
        
        try {
            PreparedStatement st = DB_CON.prepareStatement(query);
            if (null != queryParamValues) {
                for (int i = 1; i <= queryParamValues.length; i++) {
                    st.setString(i, queryParamValues[i - 1]);
                }
            }
            st.execute();
            DB_CON.commit();
        } catch(SQLException ex) {
            try {
                DB_CON.rollback();
            } catch(SQLException ex1) {
                // do nothing
            }
            return false;
        }
        return true;
    }
    
    /**
     * Fetches number of rows of a given query execution results.
     * 
     * @param query A query with unnamed parameters to be executed.
     * @param  queryParamValues Values to be bound to query parameters.
     * @return Number of rows. If an exception occurs, 0 is returned.
     */
    public static final int getNumRows(String query, String[] queryParamValues) {
        if (null == DB_CON) return 0;
        
        int rowCount = 0;
        try {
            PreparedStatement st = DB_CON.prepareStatement(query);
            if (null != queryParamValues) {
                for (int i = 1; i <= queryParamValues.length; i++) {
                    st.setString(i, queryParamValues[i - 1]);
                }
            }
            ResultSet rs = st.executeQuery();
            rs.previous();
            while (rs.next()) ++rowCount;
        } catch(SQLException ex) {
            rowCount = 0;
        }
        return rowCount;
    }
}
