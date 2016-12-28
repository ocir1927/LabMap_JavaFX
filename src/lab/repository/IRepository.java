package lab.repository;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 16.10.2016
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public interface IRepository<T,ID> {
    void add(T elem);
    Iterable<T> getAll();
    void delete(ID id);
    void update(T elem);

}
