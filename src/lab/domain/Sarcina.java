package lab.domain;

import lab.repository.Copyable;
import lab.repository.HasID;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 09.10.2016
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class Sarcina implements HasID<Integer>,Copyable<Sarcina>,Serializable {
    private Integer id;
    private String descriere;

    @Override
    public void copy(Sarcina elem) {
        this.descriere = elem.descriere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Sarcina(Integer id, String descriere) {

        this.id = id;
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return  id +" "+descriere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sarcina sarcina = (Sarcina) o;

        if (id != null ? !id.equals(sarcina.id) : sarcina.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
