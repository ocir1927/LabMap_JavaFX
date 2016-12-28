package lab.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 23.10.2016
 * Time: 00:27
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
