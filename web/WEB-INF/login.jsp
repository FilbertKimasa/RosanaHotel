<%@page import="java.util.ArrayList"%>
<%@page import="static com.hotelrosana.models.Room.getRoomTypeInWords"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>Log In | Hotel Rosana</title>
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
    <div id="content">
        <div class="heading heading-2x">LOG IN</div>
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
        <form class="form form-labelled" method="post" action="login/" autocomplete="off">
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-info-circle"></i> Fill out your credentials</div>
                <div class="panel-body">
                    <div class="formControl-group">
                        <label for="username">Username</label>
                        <input name="username" class="text-input" type="text" value="${username}">
                    </div>
                    <div class="formControl-group">
                        <label for="password">Password</label>
                        <input name="password" class="text-input" type="password" value="${password}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Log In">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <%@include file="footer.jsp"%>
</div>
</body>
</html>
