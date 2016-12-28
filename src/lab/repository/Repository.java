package lab.repository;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 06.10.2016
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
public class Repository<Elem extends HasID<ID> & Copyable,ID> implements IRepository<Elem,ID> {

    protected ArrayList<Elem> lista=new ArrayList<Elem>();
    /**
     * Constructor Repository
     * Creates a new empty ArrayList
     */
    public Repository() {
        lista=new ArrayList<Elem>();
    }

    /**
     * Returns all the posts there are in memory
     *
     * @return an ArrayList with the jobs
     */
    public ArrayList<Elem> getAll(){
        return lista;
    }

    /**
     * Appends a post to the end of the list
     * @param p -the post that will be appended
     */
    public void add(Elem p){
        lista.add(p);
    }

    /**
     * Deletes a job from the list
     * @param id - the id of the post wich will be deleted
     */
    public void delete(ID id) {
       /* if(id<=0)
            throw new Exceptie("Id trebuie sa fie mai mare decat 1");*/
        for(Elem p: lista){
            if(p.getId().equals(id)){
                lista.remove(p);
                break;
            }
        }
    }

    /**
     * Change the name and type of a job
     * @param p- The job witch will be updated
     */
    public void update(Elem p){
        for(Elem p1:lista){
            if (p1.equals(p)){
                p1.copy(p);
                break;
            }
        }
    }

    public Elem findById(ID id){
        for(Elem e:lista){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }


}