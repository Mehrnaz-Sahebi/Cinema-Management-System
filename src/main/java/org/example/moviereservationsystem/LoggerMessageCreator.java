package org.example.moviereservationsystem;

public class LoggerMessageCreator {
    public static String infoNotFound(String entityName, int id) {
        return entityName + " with id = " + id + " not found.";
    }

    public static String infoNotFound(String entityName, String name) {
        return entityName + " with name = " + name + " not found.";
    }

    public static String errorGetting(String entityName, int id) {
        return "Error getting " + entityName + " with id= " + id + " from db.";
    }

    public static String infoAlreadyExists(String entityName, int id) {
        return entityName + " with id= " + id + " already exists.";
    }

    public static String infoAlreadyExists(String entityName, String name) {
        return entityName + " with name= " + name + " already exists.";
    }

    public static String errorCreating(String entityName, int id) {
        return "Error creating " + entityName + " with id= " + id + " from db.";
    }

    public static String errorCreating(String entityName, String name) {
        return "Error creating " + entityName + " with name= " + name + " from db.";
    }

    public static String errorUpdating(String entityName, String name) {
        return "Error updating " + entityName + " with name= " + name + " from db.";
    }

    public static String errorDeleting(String entityName, String name) {
        return "Error deleting " + entityName + " with name/id " + name + " from db.";
    }

    public static String errorWritingResponse(String methodName) {
        return "Error writing response in method " + methodName;
    }

    public static String infoScheduleConflict(String message, String schedule) {
        return "Schedule conflict: " + message + " for schedule " + schedule;
    }

    public static String errorGettingAll(String entityName) {
        return "Error getting all " + entityName + " from db.";
    }

    public static String errorGettingAllWith(String entityName, String fieldName, String value) {
        return "Error getting all " + entityName + " from db with " + fieldName + " = " + value + " from db.";
    }

    public static String infoTicketReservationFailed(String message, int scheduleId, int phoneNumber) {
        return "Ticket reservation failed: " + message + " for user " + phoneNumber + "and schedule " + scheduleId + ".";
    }

    public static String infoTicketCancellationFailed(String message, int ticketId) {
        return "Ticket cancellation failed: " + message + " for ticket " + ticketId + ".";
    }
}
