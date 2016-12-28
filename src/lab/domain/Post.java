package lab.domain;

import lab.repository.Copyable;
import lab.repository.HasID;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 06.10.2016
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
public class Post implements HasID<Integer>,Copyable<Post>,Serializable {
    private Integer id;
    private String denumire;
    private String tip;

    @Override
    public void copy(Post elem) {
        this.denumire=elem.denumire;
        this.tip=elem.tip;
    }

    public Post(int id, String denumire, String tip) {
        this.id = id;
        this.denumire = denumire;
        this.tip = tip;
    }

    /**
     * Getters and setters
     */
    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    /**
     * Equals overrided method
     * Equals by id
     * @param o - the other object
     * @return true if this object and other have the same id, else it returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        if (id != post.id && !denumire.equals(((Post) o).denumire)) return false;
        return true;
    }

    @Override
    public String toString() {
        return id+" "+ denumire+ " "+ tip ;
    }
}
