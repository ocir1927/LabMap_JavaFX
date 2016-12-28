package lab.service;

import lab.domain.*;
import lab.repository.PostFileRepository;
import lab.repository.Repository;
import lab.repository.SarcinaSerializedRepository;
import lab.repository.SarcinaXMLStAX2Repository;
import lab.utils.Observable;
import lab.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 26.11.2016
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class SarcinaManagement implements Observable<Sarcina> {
    protected List<Observer<Sarcina>> observersSarcina = new ArrayList<Observer<Sarcina>>();
    ValidatorSarcina validatorSarcina=new ValidatorSarcina();
//    private SarcinaSerializedRepository repoSarcina;
       private SarcinaXMLStAX2Repository repoSarcina;

    public SarcinaManagement() {
//        repoSarcina =new SarcinaSerializedRepository("C:\\Users\\Costi\\IdeaProjects\\Lab1_1\\src\\lab\\TextFiles\\Sarcini.bin") ;
        repoSarcina=new SarcinaXMLStAX2Repository("C:\\Users\\Costi\\IdeaProjects\\Lab1_1\\src\\lab\\TextFiles\\Sarcini.xml");
    }


    public ArrayList<Sarcina> getAllSarcina(){
        // return repoSarcina.getAll();
        return  repoSarcina.getAll();
    }

    /**
     * Add a new job to the list of jobs
     * @param id -the id of the job
     * @param descriere - the name of the job
     */

    public void addSarcina(int id,String descriere) throws ValidationException,DuplicateIdException{
        Sarcina s=new Sarcina(id,descriere);
        if(findSarcinaById(id)!=null)
            throw new DuplicateIdException(DuplicateIdException.class.getName());
        validatorSarcina.validate(s);
        repoSarcina.add(new Sarcina(id,descriere));
        notifyObservers();

    }

    /**
     * Deteles a job
     * @param id - the id of the job you want ot delete
     */

    public void deleteSarcina(int id){
        repoSarcina.delete(id);
        notifyObservers();
    }


    public void updateSarcina(int id,String descriere) throws ValidationException{
        validatorSarcina.validate(new Sarcina(id,descriere));
        repoSarcina.update(new Sarcina(id,descriere));
        notifyObservers();
    }

    public Sarcina findSarcinaById(int id){
        return repoSarcina.findById(id);
    }

    /**
     * Generic method for filtering a list
     * @param l -the list to be filtered
     * @param p - the criteria for the filter
     * @param <E> -the genery type
     * @return a list of <E> filtered by a criteria
     */
    public <E> List<E> genericFilter(List<E> l, Predicate<E> p) {
        return (List<E>) l.stream()
                .filter(p)
                .collect(Collectors.toList());
    }

    /**
     * Filter a list of Sarcini by id
     * @param pred- integer
     * @return sorded list by id and filtered by pred(id>pred)
     */
    public List<Sarcina> filterSarcinaByID(Integer pred){
        Predicate<Sarcina> p=x->x.getId()>pred;
        ArrayList l=getAllSarcina();
        List<Sarcina> l1=genericFilter(l,p);
        l1.sort((Sarcina s1,Sarcina s2)->s2.getId()-s1.getId());
        return l1;
    }

    /**
     * Filter a list of Sarcini by criteria
     * @param pred- string
     * @return a sorded list alphabetical by descriere filtered by "pred"(contains the string)
     */
    public List<Sarcina> filterSarcinaByDescriere(String pred){
        Predicate<Sarcina> p=s->s.getDescriere().contains(pred);
        List<Sarcina> l=getAllSarcina();
        l=genericFilter(l,p);
        l.sort((s1,s2)->s1.getDescriere().compareTo(s2.getDescriere()));
        return l;
    }


    @Override
    public void addObserver(Observer<Sarcina> o) {
        observersSarcina.add(o);
    }

    @Override
    public void removeObserver(Observer<Sarcina> o) {
        observersSarcina.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer<Sarcina> o:observersSarcina){
            o.update(this);
        }
    }

}
