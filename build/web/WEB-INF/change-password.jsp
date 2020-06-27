<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>Change Password | Hotel Rosana</title>
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
    <div id="content">
        <div class="heading heading-2x">Change Password</div>
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
        <form class="form form-labelled" method="post" action="change-password/" autocomplete="off">
            <div class="panel">
                <div class="panel-heading"><i class="fa fa-info-circle"></i> Fill out your credentials</div>
                <div class="panel-body">
                    <div class="formControl-group">
                        <label for="cur_pass">Current Password</label>
                        <input name="cur_pass" class="text-input" type="password" value="${cur_pass}">
                    </div>
                    <div class="formControl-group">
                        <label for="new_pass">New Password</label>
                        <input name="new_pass" class="text-input" type="password" value="${new_pass}">
                    </div>
                    <div class="formControl-group">
                        <label for="conf_pass">Confirm Password</label>
                        <input name="conf_pass" class="text-input" type="password" value="${conf_pass}">
                    </div>
                    <div class="formControl-group">
                        <label></label>
                        <input type="submit" name="submit" value="Save">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <%@include file="footer.jsp"%>
</div>
</body>
</html>