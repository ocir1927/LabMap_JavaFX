package lab.repository;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 16.10.2016
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public interface HasID<ID> {
    ID getId();
    void setId(ID id);
}
