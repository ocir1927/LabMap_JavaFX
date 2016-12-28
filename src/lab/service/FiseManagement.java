package lab.service;

import javafx.geometry.Pos;
import lab.GUI.MessageAlert;
import lab.domain.*;
import lab.repository.RepoFise;
import lab.repository.SarcinaSerializedRepository;
import lab.utils.Observable;
import lab.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Costi on 05.12.2016.
 */
public class FiseManagement implements Observable<ElementFisa> {
    protected List<Observer<ElementFisa>> observersFisa = new ArrayList<Observer<ElementFisa>>();
 //   ValidatorSarcina validatorSarcina=new ValidatorSarcina();
    private RepoFise repoFise;


    public FiseManagement() {
        repoFise =new RepoFise("C:\\Users\\Costi\\IdeaProjects\\Lab1_1\\src\\lab\\TextFiles\\Fise.bin") ;
    }

    public ArrayList<ElementFisa> getAllFise(){
        // return repoFise.getAll();
        return  repoFise.getAll();
    }

    /**
     * Add a new job to the list of jobs
     */

    public void addFisa(Post p,Sarcina s) throws ExistaSarcinaDejaException{
        ArrayList<ElementFisa> fise=getAllFise();

        for(ElementFisa elf:fise){
            if (elf.getPost().equals(p)){
                if (elf.getSarcini().contains(s)){
                   throw new ExistaSarcinaDejaException("Sarcina exista deja pentru acest post");
                }
                elf.addSarcina(s);
                notifyObservers();
                return;
            }
        }

        ArrayList<Sarcina> sarcini=new ArrayList<>();
        int id=1;
        boolean gasit=false;
        while (!gasit){
            gasit = true;
            for (ElementFisa elementFisa : fise) {
                if (elementFisa.getId().equals(id)) {   gasit = false;}
            }
            if(!gasit)
                id++;
        }
        sarcini.add(s);

      //  ElementFisa s=new ElementFisa(id,p,sarcini);
  /*      if(findElemFisaById(id)!=null)
            throw new DuplicateIdException(DuplicateIdException.class.getName());*/
/*
        if(findElemFisaByPost(p)!=null)
            throw new DuplicateIdException("Exista deja");*/
        //validatorSarcina.validate(s);
        repoFise.add(new ElementFisa(id,p,sarcini));
        notifyObservers();

    }

    /**
     * Deteles a job
     * @param id - the id of the job you want ot delete
     */

    public void deleteElementFisa(int id){
        repoFise.delete(id);
        notifyObservers();
    }


    public void updateElementFisa(int id,Post p,ArrayList<Sarcina> sarcini) throws ValidationException{
       // validatorSarcina.validate(new Sarcina(id,descriere));
        repoFise.update(new ElementFisa(id,p,sarcini));
        notifyObservers();
    }

    public ElementFisa findElemFisaById(int id){
        return repoFise.findById(id);
    }

    public ElementFisa findElemFisaByPost(Post post){
        for(ElementFisa el:getAllFise()){
            if (el.getPost().equals(post))
                return el;
        }
        return null;
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

/*
    public List<Sarcina> filterSarcinaByID(Integer pred){
        Predicate<Sarcina> p=x->x.getId()>pred;
        ArrayList l=getAllSarcina();
        List<Sarcina> l1=genericFilter(l,p);
        l1.sort((Sarcina s1,Sarcina s2)->s2.getId()-s1.getId());
        return l1;
    }
*/

  /*  public List<Sarcina> filterSarcinaByDescriere(String pred){
        Predicate<Sarcina> p=s->s.getDescriere().contains(pred);
        List<ElementFisa> l=getAllSarcina();
        l=genericFilter(l,p);
        l.sort((s1,s2)->s1.getDescriere().compareTo(s2.getDescriere()));
        return l;
    }
*/

    @Override
    public void addObserver(Observer<ElementFisa> o) {
        observersFisa.add(o);
    }

    @Override
    public void removeObserver(Observer<ElementFisa> o) {
        observersFisa.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer<ElementFisa> o: observersFisa){
            o.update(this);
        }
    }
}
