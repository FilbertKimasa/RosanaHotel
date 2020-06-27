<%@page import="com.hotelrosana.models.Room"%>
<%@page import="com.hotelrosana.app.App"%>
<%@page import="com.hotelrosana.models.Booking"%>
<%@page import="static com.hotelrosana.models.Room.getRoomTypeInWords"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>Assign Room | Hotel Rosana</title>
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
        <div class="heading heading-2x">Assign Room</div>
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
        
            <%
                Booking booking = (Booking) request.getAttribute("booking");
                String roomsType = booking.getData("rooms_type");
                ArrayList<String> availableRooms = (ArrayList) request.getAttribute("availableRooms");
            %>
        <div class="panel">
            <div class="panel-heading">Available Rooms (<%= getRoomTypeInWords(roomsType)%>)</div>
            <div class="panel-body">
                <div class="heading heading-caption" style="text-align: center; margin-bottom: 1em; color: #990000;">Choose any of the following</div>
                <div class="list-rooms">
                    <% for (String roomNo: availableRooms) { %>
                    <div class="list-item"><span><%= roomNo%></span></div>
                    <% } %>
                </div>
                <div class="clear-float"></div>
                <form name="assignRoomForm" id="assignRoomForm" class="form form-labelled" method="post" action="assign-room/?bid=<%= booking.getData("booking.booking_id")%>" autocomplete="off">
                    <div class="formControl-group">
                        <label for="room_no">Room Number</label>
                        <input type="text" class="text-input" name="room_no" value="${roomNo}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Submit">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp"%>
</div>
<script type="text/javascript">
var getRoomNumber = {
    "roomNumbers": Array.prototype.slice.call(document.querySelectorAll(".list-rooms .list-item > span:first-child")),
    "formField": assignRoomForm.room_no,
    "get": function() {
        this.roomNumbers.forEach(function(e, i) {
            e.parentElement.addEventListener("click", function() {
                this.formField.value = e.innerText;
                this.formField.focus();
            }.bind(this), false);
        }.bind(this));
    }
};

getRoomNumber.get();
</script>
</body>
</html>
