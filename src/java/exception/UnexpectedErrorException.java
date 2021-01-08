package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitor Fidalgo
 */
public class UnexpectedErrorException extends Exception{
    public UnexpectedErrorException(String exceptionMessage){
        super("An unexpected error occured, please try later.");
        Logger.getLogger(UnexpectedErrorException.class.getName()).log(Level.SEVERE, exceptionMessage);
    }
}
