package lab.service;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 26.11.2016
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class DuplicateIdException extends Exception {
    public DuplicateIdException(String message) {
        super(message);
    }
}
