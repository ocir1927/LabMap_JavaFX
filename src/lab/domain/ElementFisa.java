package lab.domain;

import lab.repository.Copyable;
import lab.repository.HasID;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Costi on 05.12.2016.
 */
public class ElementFisa implements HasID<Integer>,Copyable<Sarcina>,Serializable {

    int id;
    Post post;
    ArrayList<Sarcina> sarcini;

    public ElementFisa(int id, Post post, ArrayList<Sarcina> sarcini) {
        this.id = id;
        this.post = post;
        this.sarcini = sarcini;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ArrayList<Sarcina> getSarcini() {
        return sarcini;
    }

    public void setSarcini(ArrayList<Sarcina> sarcini) {
        this.sarcini = sarcini;
    }

    public void addSarcina(Sarcina s){
        this.sarcini.add(s);
    }

    @Override
    public String toString() {
        return  id +
                "---"+post.toString() +
                "---"+ sarcini.toString();
    }

    @Override
    public void copy(Sarcina elem) {

    }
}
