<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>${heading} | Hotel Rosana</title>
<base href="http://localhost:8080/RosanaHotel/">
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=yes">
<meta name="description" content="Rosana Hotel">
<meta name="robots" content="none">
<link href="assets/images/favicon.png" rel="shortcut icon" type="image/png">
<link href="assets/styles/main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/alt-main.css" type="text/css" rel="stylesheet" media="all">
<link href="assets/styles/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<div id="global-container">
    <div class="panel panel-message" style="width: 60%; margin: 1em auto;">
        <div class="panel-heading"><i class="fa fa-info-circle"></i> ${heading}</div>
        <div class="panel-body">
            ${message}
            <% if (null != request.getAttribute("with_back_link")) { %>
            <br><br>
                <% if (null != request.getAttribute("back_link_label")) { %>
            <a href="${back_link}" class="button button-large">&laquo; ${back_link_label}</a>
                <% } else { %>
            <a href="${back_link}" class="button button-large">&laquo; Go Back</a>
                <% } %>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
