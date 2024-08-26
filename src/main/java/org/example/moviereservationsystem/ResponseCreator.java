package org.example.moviereservationsystem;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseCreator {
    public static void sendAlreadyExistsError(HttpServletResponse response, String entityName) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write("This " + entityName + " already exists.");
    }

    public static void sendNotFoundError(HttpServletResponse response, String entityName) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write(entityName + " doesn't exist.");
    }

    public static void sendScheduleConflictError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write("There is a schedule conflict:" + errorMessage);
    }

    public static void sendTicketReservationError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write("Ticket reservation failed: " + errorMessage);
    }

    public static void sendTicketCancellationError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Ticket cancellation failed: " + errorMessage);
    }
    public static void unauthorizedError(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("You don't have access.");
    }
}
