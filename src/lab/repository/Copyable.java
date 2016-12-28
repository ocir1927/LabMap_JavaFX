package lab.repository;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 17.10.2016
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public interface Copyable<E> {
    void copy(E elem);
}
