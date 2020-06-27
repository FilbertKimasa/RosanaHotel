package com.hotelrosana.app;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GalleryServlet", urlPatterns = { "/gallery", "/gallery/" })
public class Gallery extends App {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        request.setAttribute("flashedMessages", getFlashedMessages());
        request.getRequestDispatcher("/WEB-INF/gallery.jsp").forward(request, response);
    }
}
