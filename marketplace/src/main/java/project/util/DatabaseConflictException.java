package project.util;

/**
 * Database already contains the entity 
 * or the entity could not be persisted to the database for one reason or another
 * corresponds to status code 409
 */
public class DatabaseConflictException extends Exception {

}
