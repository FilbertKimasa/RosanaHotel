package com.hotelrosana.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for paginating SQL query results.
 */
public class Pagination {
    private Connection dbCon;
    public String query;
    String[] queryParamValues;
    private int rowCount, currentPage, limit, offset, numPages, nextPage, prevPage;
    private ResultSet resultSet;
    private HttpServletRequest request;
    
    /**
     * Sets a servlet request object to be used by the class.
     * 
     * @param request The {@code HttpServletRequest} object.
     */
    public final void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    /**
     * Sets a database connection object to be used by the class.
     * 
     * @param dbCon The {@code Connection} object.
     */
    public final void setConnection(Connection dbCon) {
        this.dbCon = dbCon;
    }
    
    /**
     * Sets a query to be used to determine row count which in turn
     * is used to calculate the number of pages.
     * <p>The query should contain only a {@code SELECT} statement
     * with one or several columns which will be concatenated with other columns
     * when fetching data with {@link #getPageData()}.<br>
     * It should not contain a {@code LIMIT} or an {@code OFFSET}.</p>
     * <br>
     * <p>Example of a required query:
     * <br>&nbsp;&nbsp; - SELECT column FROM table WHERE column = ?</p>
     * 
     * <p>Note:<br>All SQL keywords should be in capital letters.<br>
     * If the query contains sub-queries then the SQL keywords
     * of the sub-queries should be in lowercase.</p>
     * 
     * @param query The SQL query with unnamed parameters.
     * @param queryParamValues Unnamed parameters values used in the query.
     */
    public final void setRowCount(String query, String[] queryParamValues) {
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            if (null != queryParamValues) {
                for (int i = 1; i <= queryParamValues.length; i++) {
                    st.setString(i, queryParamValues[i - 1]);
                }
            }
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                rowCount += 1;
            }
            
            this.query = query.trim();
            this.queryParamValues = queryParamValues;
            
        } catch(SQLException ex) {
            rowCount = 0;
        }
    }
    
    public final int getRowCount() {
        return rowCount;
    }
    
    public final void setLimit(int limit) {
        this.limit = limit;
    }
    
    private void setNumPages() {
        numPages = (int) Math.ceil((double) rowCount / limit);
    }
    
    private void setCurrentPage() {
        currentPage = 1;
        if (null != request && null != request.getParameter("page")) {
            if (currentPage > 0 && currentPage <= numPages) {
                try {
                    currentPage = Integer.parseInt(request.getParameter("page"));
                } catch(NumberFormatException ex) {
                    currentPage = 1;
                }
            } else {
                currentPage = 1;
            }
        }
    }
    
    public final int getCurrentPage() {
        setNumPages();
        setCurrentPage();
        
        return currentPage;
    }
    
    private void setNextPage() {
        if (currentPage < numPages) {
            nextPage = currentPage + 1;
        } else {
            nextPage = 1;
        }
    }
    
    private void setPrevPage() {
        if (currentPage > 1) {
            prevPage = currentPage - 1;
        } else {
            prevPage = 1;
        }
    }
    
    /**
     * Gets pagination links for the environment set.
     * 
     * @param pageURL URL of a page to which links point.
     * @param linksLimit Number of links to be displayed.
     * @param buttonText Button link text if {@code linksLimit} is set to 0.  
     * @return A {@code String} of pagination links HTML code.
     */
    public final String getPaginationLinks(String pageURL, int linksLimit, String buttonText) {
        String paginationLinks = "";
        setNumPages();
        setCurrentPage();
        setPrevPage();
        setNextPage();
        if (numPages > 1) {
            if (linksLimit > 0) {
                int curPageAsideLinks = (int) Math.floor(linksLimit / 2);
                
                // links before current page
                if (currentPage >= curPageAsideLinks) {
                    paginationLinks += "<a href=\"" + pageURL + "page=1\">First</a><a href=\"" + pageURL + "page=" + prevPage + "\">Previous</a>";
                }
                for (int i = currentPage - curPageAsideLinks; i < currentPage; i++) {
                    if(i > 0 && i <= numPages) {
                        paginationLinks += "<a href=\"" + pageURL + "page=" + i + "\">" + i + "</a>";;
                    }
                }
                
                // current page
                paginationLinks += "<a href=\"" + pageURL + "page=" + currentPage + "\" class=\"current-page\">" + currentPage + "</a>";
                
                // links after current page
                for (int i = currentPage + 1; i <= currentPage + curPageAsideLinks; i++) {
                    if (i > 0 && i <= numPages) {
                        paginationLinks += "<a href=\"" + pageURL + "page=" + i + "\">" + i + "</a>";
                    }
                }
                if ((currentPage < numPages) && (numPages > (curPageAsideLinks + 1))) {
                    paginationLinks += "<a href=\"" + pageURL + "page=" + nextPage + "\">Next</a><a href=\"" + pageURL + "page=" + numPages + "\">Last</a>";
                }
            } else {
                paginationLinks += "<a href=\"" + pageURL + "\">" + buttonText + "</a>";
            }
        }
        
        return paginationLinks;
    }
    
    /**
     * Gets pagination links of Previous and Next links with 
     * additional First and Last links for the environment set.
     * 
     * @param pageURL URL of a page to which links point.  
     * @return A {@code String} of pagination links HTML code.
     * An empty string is returned if the number of pages is
     * less or equal to 1 or if the limit set is not equal to 1.
     */
    public final String getPrevNextPaginationLinks(String pageURL) {
        String paginationLinks = "";
        setNumPages();
        setCurrentPage();
        setPrevPage();
        setNextPage();
        if (numPages > 1 && limit == 1) {
            if (currentPage > 1 && currentPage < numPages) {
                paginationLinks = "<a href=\"" + pageURL + "page=1\">First</a><a href=\"" + pageURL + "page=" + prevPage + "\">Previous</a><a href=\"" + pageURL + "page=" + nextPage + "\">Next</a><a href=\"" + pageURL + "page=" + numPages + "\">Last</a>";
            } else if (currentPage > 1 && currentPage == numPages) {
                paginationLinks = "<a href=\"" + pageURL + "page=1\">First</a><a href=\"" + pageURL + "page=" + prevPage + "\">Previous</a>";
            } else if (currentPage == 1) {
                paginationLinks = "<a href=\"" + pageURL + "page=" + nextPage + "\">Next</a><a href=\"" + pageURL + "page=" + numPages + "\">Last</a>";
            }
        }
        
        return paginationLinks;
    }
    
    /**
     * Fetches data for a page for column(s) selected
     * in {@link #setRowCount(java.lang.String, java.lang.String[])} method.
     * 
     * @return {@code ResultSet} of fetched data.
     */
    public ResultSet getPageData() {
        setNumPages();
        setCurrentPage();
        if (null == query) {
            return null;
        }
        
        offset = (currentPage * limit) - limit;
        query += " LIMIT " + limit + " OFFSET " + offset;
    
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            if (null != queryParamValues) {
                for (int i = 1; i <= queryParamValues.length; i++) {
                    st.setString(i, queryParamValues[i - 1]);
                }
            }
            return st.executeQuery();
        } catch(SQLException ex) {
            return null;
        }
    }
    
    /**
     * Fetches data for a page.
     * @param columns Array of columns to be fetched.
     * @return {@code ResultSet} of fetched data.
     */
    public ResultSet getPageData(String[] columns) {
        setNumPages();
        setCurrentPage();
        if (null == query || null == columns) {
            return null;
        }
        
        offset = (currentPage * limit) - limit;
        String initialQueryColumn = query.substring(query.indexOf("SELECT") + 6, query.indexOf("FROM") - 1).trim();
        String addedColumns = String.join(", ", columns);
        String newQuery = "SELECT " + initialQueryColumn + ", " + addedColumns + " " + query.substring(query.indexOf("FROM") - 1);
        query = newQuery + " LIMIT " + limit + " OFFSET " + offset;
    
        try {
            PreparedStatement st = dbCon.prepareStatement(query);
            if (null != queryParamValues) {
                for (int i = 1; i <= queryParamValues.length; i++) {
                    st.setString(i, queryParamValues[i - 1]);
                }
            }
            return st.executeQuery();
        } catch(SQLException ex) {
            return null;
        }
    }
    
    public Pagination() {
        query = null;
        queryParamValues = null;
        rowCount = 0;
        resultSet = null;
        currentPage = 1;
        limit = 10;
        request = null;
    }
}

