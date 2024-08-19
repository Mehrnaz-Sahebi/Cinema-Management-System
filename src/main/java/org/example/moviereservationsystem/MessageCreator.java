package org.example.moviereservationsystem;

public class MessageCreator {
    public static String notFound(String entityName, int id){
        return entityName+" with id= "+id+" not found.";
    }
    public static String errorGetting(String entityName, int id){
        return "Error getting "+entityName+" with id= "+id+ " from db.";
    }
    public static String alreadyExists(String entityName, int id){
        return entityName+" with id= "+id+" already exists.";
    }
    public static String errorCreating(String entityName, int id){
        return "Error creating "+entityName+" with id= "+id+ " from db.";
    }
}
