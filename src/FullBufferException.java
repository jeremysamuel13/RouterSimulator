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
 * Exception for when a router's buffer is full
 */

public class FullBufferException extends RuntimeException {
    public FullBufferException(String message){
        super(message);
    }

}
