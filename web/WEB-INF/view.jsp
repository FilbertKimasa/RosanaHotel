<%@page import="com.hotelrosana.app.App"%>
<%@page import="com.hotelrosana.models.Booking"%>
<%@page import="com.hotelrosana.models.Room"%>
<%@page import="static com.hotelrosana.models.Room.getRoomTypeInWords"%>
<%@page import="com.hotelrosana.util.Pagination"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>${pageTitle} | Hotel Rosana</title>
<base href="http://localhost:8080/RosanaHotel/">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=yes">
<meta name="description" content="Rosana Hotel">
<meta name="robots" content="none">
<link href="assets/images/favicon.png" rel="shortcut icon" type="image/png">
<link href="assets/styles/main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/alt-main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<div id="global-container">
    <%@include file="header.jsp"%>
    <%@include file="admin-nav.jsp"%>
    <div id="content" class="centered-text lr-padded">
        <div class="heading heading-2x">${pageTitle}</div>
        <%
            ArrayList<String> flashedMessages = (ArrayList)request.getAttribute("flashedMessages");
            if (flashedMessages.size() > 0) {
        %>
        <div class="panel panel-message">
            <div class="panel-heading"><i class="fa fa-info-circle"></i> Info</div>
            <div class="panel-body">
                <%
                    for (String message: flashedMessages) {
                %>
                <p>- <%= message%></p>
                <%  }  %>
            </div>
        </div><br>
        <% } %>
        <%  String section = request.getParameter("sec");
            Pagination pagination = (Pagination) request.getAttribute("pagination");
            if (section.equalsIgnoreCase("bookings")) { %>
        <div class="booking-view"> 
            <%
                int rowCount = pagination.getRowCount();
                int currentPage = pagination.getCurrentPage();
                if (rowCount < 1) {
            %>
            <p>Nothing to show</p>
            <%  } else {
                    ResultSet bookingsList = pagination.getPageData();
                    while(bookingsList.next()) {
                        Booking booking = new Booking(bookingsList.getString("booking_id"));
                        String bookingID = booking.getData("booking.booking_id");
                    %>
            <div class="panel">
                <div class="panel-heading">Booking Info | <%= currentPage%> of <%= rowCount%> | <%= booking.getTimeCount()%> ago</div>
                <div class="panel-body">
                    <div class="bv-box">
                        <div class="heading heading-caption"><i class="fa fa-user-circle"></i> Guest</div>
                        <p>Name: <%= App.escapeHTML(booking.getData("name"))%></p>
                        <p>Email: <%= App.escapeHTML(booking.getData("email"))%></p>
                        <p>Mobile: <%= App.escapeHTML(booking.getData("mobile"))%></p>
                        <p>Nationality: <%= App.escapeHTML(booking.getData("nationality"))%></p>
                    </div>
                    <div class="bv-box">
                        <div class="heading heading-caption"><i class="fa fa-bed"></i> Accommodation Specs</div>
                        <p>Rooms: <%= booking.getData("num_rooms")%> of <%= getRoomTypeInWords(booking.getData("rooms_type"))%> type</p>
                        <p>Date of Arrival: <%= new SimpleDateFormat("MMM dd, yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(booking.getData("arrival")))%></p>
                        <p>Date of Departure: <%= new SimpleDateFormat("MMM dd, yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(booking.getData("departure")))%></p>
                    </div>
                    <div class="bv-box">
                        <div class="heading heading-caption"><i class="fa fa-money"></i> Payment Info</div>
                        <p>Amount Paid (USD): <%= App.escapeHTML(booking.getData("amount"))%></p>
                        <p>Bank: <%= App.escapeHTML(booking.getData("bank"))%></p>
                        <p>Reference Numbers: <%= App.escapeHTML(booking.getData("ref_no"))%></p>
                    </div>
                    <% if (booking.hasAssignedRoom()) { %>
                    <div class="bv-box">
                        <div class="heading heading-caption"><i class="fa fa-check"></i> Assigned Rooms</div>
                        <p><%= String.join(", ", booking.getAssignedRooms())%></p>
                    </div>
                    <% } %>
                </div>
            </div>
            <div class="booking-action-bar pagination-links"><a href="assign-room/?bid=<%= bookingID%>">Assign Room</a><% if (!booking.isRead()) out.print("<a href=\"do-action/?a=mark-read&amp;ent=booking&amp;bid=" + bookingID + "\">Mark as Read</a>"); %><a href="do-action/?a=delete&amp;ent=booking&amp;bid=<%= bookingID%>">Delete</a><% out.print(pagination.getPrevNextPaginationLinks((String) request.getAttribute("pageURL"))); %></div>
        <%          }
                } %>
        </div>
        <%  } else if (section.equalsIgnoreCase("rooms")) { %>
        <div class="panel">
            <div class="panel-heading"><i class="fa fa-th-large"></i> All Rooms - The List</div>
            <div class="panel-body">
                <div class="list-rooms">
                    <%  ResultSet roomsList = pagination.getPageData();
                        while (roomsList.next()) {
                            Room room = new Room(roomsList.getString("room_no"));
                            if (room.getData("occupied").equals("0")) {
                    %>
                    <div class="list-item"><span><%= room.getData("room_no")%></span><span><%= getRoomTypeInWords(room.getData("type"))%></span><span>Not Occupied</span><div class="li-opts fade-in"><a href="do-action/?a=delete&amp;ent=room&amp;room-no=<%= room.getData("room_no")%>" title="Delete This Room"><i class="fa fa-trash"></i></a></div></div>
                    <%      } else if (room.getData("occupied").equals("1")) { %>
                    <div class="list-item"><span><%= room.getData("room_no")%></span><span><%= getRoomTypeInWords(room.getData("type"))%></span><span><%= room.getBookingData("name")%></span><span><%= room.getBookingData("email")%></span><span>Arrival: <%= room.getBookingData("arrival")%></span><span>Departure: <%= room.getBookingData("departure")%></span><div class="li-opts fade-in"><a href="do-action/?a=delete&amp;ent=room&amp;room-no=<%= room.getData("room_no")%>" title="Delete This Room"><i class="fa fa-trash"></i></a></div></div>
                    <%        }
                        } %>
                </div>
                <div class="clear-float"></div>
                <div class="pagination-links"><%= pagination.getPaginationLinks((String) request.getAttribute("pageURL"), 7, null)%></div>
                <form class="form form-labelled" id="addRoomForm" method="post" action="view/?sec=rooms&amp;page=<%= pagination.getCurrentPage()%>" autocomplete="off">
                    <div class="heading heading-caption">Add a New Room</div>
                    <div class="formControl-group">
                        <label for="room_no">Room Number</label>
                        <input type="text" class="text-input" name="room_no" value="${room_no}">
                    </div>
                    <div class="formControl-group">
                        <label for="room_type">Type of Room</label>
                        <select name="room_type">
                            <% String roomType = (String) request.getAttribute("room_type"); %>
                            <option value="0"<% if (roomType.equals("0")) out.print(" selected");%>>Choose</option>
                            <option value="1"<% if (roomType.equals("1")) out.print(" selected");%>>Standard</option>
                            <option value="2"<% if (roomType.equals("2")) out.print(" selected");%>>Classic</option>
                            <option value="3"<% if (roomType.equals("3")) out.print(" selected");%>>Superior</option>
                            <option value="4"<% if (roomType.equals("4")) out.print(" selected");%>>Family Suite</option>
                        </select>
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
        <%  }  %>
    </div>
    <%@include file="footer.jsp"%>
</div>
<div id="room-preview" class="list-rooms fade-in"><div id="preview-content"></div><a href="#" class="close-button">&times;</a></div>
<script type="text/javascript">
var prevRoom = {
    "thumbnails": Array.prototype.slice.call(document.querySelectorAll(".list-rooms .list-item")),
    "preview": document.getElementById("room-preview"),
    "previewContent": document.getElementById("preview-content"),
    "closePreview": function() {
        "use strict";
        var closeButtons = Array.prototype.slice.call(document.querySelectorAll(".close-button"));
        closeButtons.forEach(function(e) {
            e.addEventListener("click", function(ev) {
                ev.preventDefault();
                e.parentElement.style.display = "none";
            });
        });
    },
    "showPreview": function() {
        this.preview.style.cssText = "display: block; top: " + (window.pageYOffset + 200) + "px;";
    },
    "hidePreview": function() {
        this.preview.style.display = "none";
    },
    "init": function() {
        if (this.thumbnails.length) {
            this.thumbnails.forEach(function(e, i) {
                e.addEventListener("click", function() {
                    this.previewContent.innerHTML = e.outerHTML;
                    this.showPreview();
                }.bind(this), false);
            }.bind(this));
            this.closePreview();
        }
    }
};

prevRoom.init();
</script>
</body>
</html>
