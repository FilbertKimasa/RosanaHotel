<%@page import="java.util.ArrayList"%>
<%@page import="static com.hotelrosana.models.Room.getRoomTypeInWords"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>Booking | Hotel Rosana</title>
<base href="http://localhost:8080/RosanaHotel/">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=yes">
<meta name="description" content="Rosana Hotel">
<link href="assets/images/favicon.png" rel="shortcut icon" type="image/png">
<link href="assets/styles/main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/alt-main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<div id="global-container">
    <%@include file="header.jsp"%>
    <div id="content">
        <div class="heading heading-2x">BOOKING</div>
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
        <%  }  %>
        <% String step = request.getParameter("step"); %>
        <% if (step.equals("1")) { %>
        <form class="form form-labelled" method="post" action="book/?step=1" autocomplete="off">
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-bed"></i> Choose Your Rooms</div>
                <div class="panel-body">
                    <div class="formControl-group">
                        <label for="rooms_type">Type of Rooms</label>
                        <select name="rooms_type">
                            <% String roomsType = (String) request.getAttribute("rooms_type"); %>
                            <option value="0"<% if (roomsType.equals("0")) out.print(" selected");%>>Choose</option>
                            <option value="1"<% if (roomsType.equals("1")) out.print(" selected");%>>Standard</option>
                            <option value="2"<% if (roomsType.equals("2")) out.print(" selected");%>>Classic</option>
                            <option value="3"<% if (roomsType.equals("3")) out.print(" selected");%>>Superior</option>
                            <option value="4"<% if (roomsType.equals("4")) out.print(" selected");%>>Family Suite</option>
                        </select>
                    </div>
                    <div class="formControl-group">
                        <label for="num_rooms">Number of Rooms</label>
                        <select name="num_rooms">
                            <% String numRooms = (String) request.getAttribute("num_rooms"); %>
                            <option value="0"<% if (numRooms.equals("0")) out.print(" selected");%>>Choose</option>
                            <option value="1"<% if (numRooms.equals("1")) out.print(" selected");%>>1</option>
                            <option value="2"<% if (numRooms.equals("2")) out.print(" selected");%>>2</option>
                            <option value="3"<% if (numRooms.equals("3")) out.print(" selected");%>>3</option>
                        </select>
                    </div>
                    <div class="formControl-group">
                        <label for="arrival">Arrival</label>
                        <input name="arrival" class="text-input" type="text" placeholder="dd-mm-yyyy" value="${arrival}">
                    </div>
                    <div class="formControl-group">
                        <label for="departure">Departure</label>
                        <input name="departure" class="text-input" type="text" placeholder="dd-mm-yyyy" value="${departure}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Check Availability">
                    </div>
                </div>
            </div>
        </form>
        <% } else if (step.equals("2")) { %>
        <form class="form form-labelled" method="post" action="book/?step=2" autocomplete="off">
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-user"></i> Your Information</div>
                <div class="panel-body">
                    <div class="formControl-group">
                        <label for="f_name">First Name</label>
                        <input class="text-input" name="f_name" type="text" value="${f_name}">
                    </div>
                    <div class="formControl-group">
                        <label for="l_name">Last Name</label>
                        <input class="text-input" name="l_name" type="text" value="${l_name}">
                    </div>
                    <div class="formControl-group">
                        <label for="email">Email</label>
                        <input class="text-input" name="email" type="text" value="${email}">
                    </div>
                    <div class="formControl-group">
                        <label for="mobile">Mobile</label>
                        <input class="text-input" name="mobile" type="text" value="${mobile}">
                    </div>
                    <div class="formControl-group">
                        <label for="nationality">Nationality</label>
                        <input class="text-input" name="nationality" type="text" value="${nationality}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Next">
                    </div>
                </div>
            </div>
        </form>
        <% } else if (step.equals("3")) { %>
        <form class="form form-labelled" method="post" action="book/?step=3" autocomplete="off">
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-money"></i> Payment Information</div>
                <div class="panel-body">
                    <div class="formControl-group">
                        <label for="amount">Amount Paid (USD)</label>
                        <input class="text-input" name="amount_paid" type="text" value="${amount_paid}">
                    </div>
                    <div class="formControl-group">
                        <label for="bank">Bank</label>
                        <input class="text-input" name="bank" type="text" value="${bank}">
                    </div>
                    <div class="formControl-group">
                        <label for="ref_nums">Reference Numbers</label>
                        <input class="text-input" name="ref_nums" type="text" value="${ref_nums}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Next">
                    </div>
                </div>
            </div>
        </form>    
        <% } else if (step.equals("4")) { %>
        <div id="booking-inf-prv">
            <div class="heading heading-caption">Review Your Information</div><br>
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-bed"></i> Your Rooms</div>
                <div class="panel-body">
                    <p>You chose ${num_rooms} rooms of <%= getRoomTypeInWords((String) session.getAttribute("rooms_type"))%> type</p>
                    <p>Date of Arrival: ${arrival}</p>
                    <p>Date of Departure: ${departure}</p>
                    <br><a class="button" href="book/?step=1">change</a>
                </div>
            </div><br>
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-user"></i> About You</div>
                <div class="panel-body">
                    <p>Name: ${f_name} ${l_name}</p>
                    <p>Email: ${email}</p>
                    <p>Mobile: ${mobile}</p>
                    <p>Nationality: ${nationality}</p>
                    <br><a class="button" href="book/?step=2">change</a>
                </div>
            </div><br>
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-money"></i> About Payment</div>
                <div class="panel-body">
                    <p>Bank: ${bank}</p>
                    <p>Amount Paid: ${amount_paid} USD</p>
                    <p>Reference Numbers: ${ref_nums}</p>
                    <br><a class="button" href="book/?step=3">change</a>
                </div>
            </div><br>
            <form class="form" method="post" action="book/?step=4" autocomplete="off">
                <input type="submit" name="submit" value="Finish">
            </form>    
        </div>
        <% } %>
    </div>
    <%@include file="footer.jsp"%>
</div>
</body>
</html>
