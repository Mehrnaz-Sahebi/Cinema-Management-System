package org.example.moviereservationsystem;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseCreator {
    public static void sendAlreadyExistsError(HttpServletResponse response, String entityName) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write("This "+ entityName +" already exists.");
    }
}
