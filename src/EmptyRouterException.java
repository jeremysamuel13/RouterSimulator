/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #4
 */

/**
 * Exception for when there is no packets in a router
 */

public class EmptyRouterException extends RuntimeException {
    public EmptyRouterException(String message){
        super(message);
    }

}
