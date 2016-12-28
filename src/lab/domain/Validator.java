package lab.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 23.10.2016
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
