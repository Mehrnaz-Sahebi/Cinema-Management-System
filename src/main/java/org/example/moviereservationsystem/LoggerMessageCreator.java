package org.example.moviereservationsystem;

public class LoggerMessageCreator {
    public static String infoNotFound(String entityName, int id){
        return entityName+" with id= "+id+" not found.";
    }public static String infoNotFound(String entityName, String name){
        return entityName+" with id= "+name+ " not found.";
    }
    public static String errorGetting(String entityName, int id){
        return "Error getting "+entityName+" with id= "+id+ " from db.";
    }
    public static String infoAlreadyExists(String entityName, int id){
        return entityName+" with id= "+id+" already exists.";
    }
    public static String infoAlreadyExists(String entityName, String name){
        return entityName+" with id= "+name+" already exists.";
    }
    public static String errorCreating(String entityName, int id){
        return "Error creating "+entityName+" with id= "+id+ " from db.";
    }
    public static String errorCreating(String entityName, String name){
        return "Error creating "+entityName+" with id= "+name+ " from db.";
    }
    public static String errorUpdating(String entityName, String name){
        return "Error updating "+entityName+" with id= "+name+ " from db.";
    }
    public static String errorWritingResponse(String methodName){
        return "Error writing response in method "+methodName;
    }
}
