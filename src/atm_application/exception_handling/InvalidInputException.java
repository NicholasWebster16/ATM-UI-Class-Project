package atm_application.exception_handling;

import java.util.ArrayList;

/************************************
 * Note about exception:
 * By accepting an ArrayList of strings
 * the exception allows for detailed
 * alerts that explain multiple errors
 * to the user to avoid making the user
 * attempt multiple inputs to discover
 * multiple problems.
 ************************************/
public class InvalidInputException extends Exception {
    public InvalidInputException(ArrayList<String> errors){
        super("InvalidInputException");
        new ExceptionWindow("Invalid Input Error(s): ", errors);
    }
}
