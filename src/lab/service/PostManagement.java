package lab.service;

import lab.domain.*;
import lab.repository.PostFileRepository;
import lab.repository.Repository;
import lab.repository.SarcinaSerializedRepository;
import lab.utils.Observable;
import lab.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 09.10.2016
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class PostManagement implements Observable<Post> {
    protected List <Observer<Post>> observersPost = new ArrayList<Observer<Post>>();
//    private Repository<Post,Integer> repoPost;
    ValidatorPost validatorPost=new ValidatorPost();
    private PostFileRepository repoPost;


    public PostManagement() {
        repoPost=new PostFileRepository("C:\\Users\\Costi\\IdeaProjects\\Lab1_1\\src\\lab\\TextFiles\\Posturi.txt");
    }

    public ArrayList<Post> getAllPosts(){
       //return repoPost.getAll();
        return repoPost.getAll();
    }

    /**
     * Add a new job to the list of jobs
     * @param id -the id of the job
     * @param denumire - the name of the job
     * @param tip - the type of the job(parttime/fulltime)
     */
    public void addPost(int id,String denumire,String tip) throws ValidationException,DuplicateIdException{
        Post p=new Post(id,denumire,tip);
        if(repoPost.findById(p.getId())!=null) {
            throw new DuplicateIdException(DuplicateIdException.class.getName());
        }
        validatorPost.validate(p);
        repoPost.add(p);
        notifyObservers();

    }

    /**
     * Deteles a job
     * @param id - the id of the job you want ot delete
     */
    public void deletePost(int id){
        repoPost.delete(id);
        notifyObservers();
    }

    /**
     * Updates a job
     * @param id -the id of the job you want to updade
     * @param denumire -the new name of the job
     * @param tip - the new type of the job
     */
    public void updatePost(int id,String denumire,String tip) throws ValidationException{
        validatorPost.validate(new Post(id,denumire,tip));
        repoPost.update(new Post(id,denumire,tip));
        notifyObservers();
    }

    public Post findPostById(int id){
        return repoPost.findById(id);
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
     * Filter a list of Post by a criteria
     * @param pred - a string to compare the type
     * @return a list of posts having the type "pred"
     */
    public List<Post> filterPostByType(String pred){
        Predicate<Post> p=x->x.getTip().equals(pred);
        ArrayList l=getAllPosts();
        return genericFilter(l,p);
    }

    /**
     * Filter a list of Post by a criteria
     * @param pred -a string
     * @return a list of Post filtered by name(name contains string "pred")
     */
    public List<Post> filterPostByName(String pred){
        Predicate<Post> p=x->x.getDenumire().toLowerCase().contains(pred.toLowerCase());
        ArrayList l=getAllPosts();
        return genericFilter(l,p);
    }


    @Override
    public void addObserver(Observer<Post> o) {
        observersPost.add(o);
    }

    @Override
    public void removeObserver(Observer<Post> o) {
        observersPost.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer<Post> o:observersPost){
            o.update(this);
        }
    }
}
