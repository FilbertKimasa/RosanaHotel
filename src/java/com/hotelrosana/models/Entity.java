package com.hotelrosana.models;

public interface Entity {
    
    /**
     * Saves a database table entry.
     * 
     * @param columns Columns whose data is inserted.
     * @param values Values to the columns.
     * @return {@code true} on success and {@code false} on failure.
     */
    public boolean save(String[] columns, String[] values);
    
    /**
     * Saves a database table entry.
     * 
     * @param table Table to which data is inserted.
     * @param columns Columns whose data is inserted.
     * @param values Values to the columns.
     * @return {@code true} on success and {@code false} on failure.
     */
    public boolean save(String table, String[] columns, String[] values);
    
    /**
     * Deletes an entry initialized with a given primary key value.
     * 
     * @return {@code true} on success and {@code false} on failure.
     */
    public boolean delete();
    
    /**
     * Fetches data of a given column of an entry.
     * 
     * @param column The column whose data is fetched.
     * @return A {@code String} of fetched data on success
     * and {@code null} on failure.
     */
    public String getData(String column);
    
    /**
     * Updates column data of an entry.
     * 
     * @param column Column to be updated.
     * @param newValue New value of the column.
     * @return {@code true} on success and {@code false} on failure.
     */
    public boolean update(String column, String newValue);
    
    /**
     * Checks whether an entry exists in database.
     * 
     * @return {@code true} if an entry exists or {@code false} otherwise.
     * Also {@code false} is returned on failure.
     */
    public boolean find();
}
