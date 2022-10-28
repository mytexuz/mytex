package uz.enterprise.mytex.exceptions;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 11:16 AM 10/27/22 on Thursday in October
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
