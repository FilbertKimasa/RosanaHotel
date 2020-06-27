<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="panel" id="admin-nav">
    <div class="panel-heading"><i class="fa fa-cog"></i> Activities</div>
    <div class="panel-body">
        <ul>
            <li><a href="change-password/"><i class="fa fa-dot-circle-o"></i> Change Password</a></li>
            <li><a href="view/?sec=bookings&amp;cat=all"><i class="fa fa-dot-circle-o"></i> View Bookings</a>
                <ul>
                    <li><a href="view/?sec=bookings&amp;cat=all"><i class="fa fa-circle"></i> All</a>
                    <li><a href="view/?sec=bookings&amp;cat=read"><i class="fa fa-circle"></i> Read</a>
                    <li><a href="view/?sec=bookings&amp;cat=unread"><i class="fa fa-circle"></i> Unread</a>
                </ul>
            </li>
            <li><a href="view/?sec=rooms"><i class="fa fa-dot-circle-o"></i> View Rooms</a></li>
            <li><a href="logout/"><i class="fa fa-dot-circle-o"></i> Log Out</a></li>
        </ul>
    </div>
</div>
