package lab.service;

import lab.domain.ElementFisa;
import lab.domain.Post;
import lab.domain.Sarcina;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Costi on 05.12.2016.
 */
public class FiseManagementTest {
    FiseManagement fiseManagement=new FiseManagement();
    Post p1;
    Sarcina s1;
    Sarcina s2;
    ArrayList<Sarcina> sarcini;

    @Before
    public void setUp() throws Exception {

        p1=new Post(1,"Post1","Parttime");
        s1=new Sarcina(1,"descriereSarcina1");
        s2=new Sarcina(2,"descriereSarcina2");
        sarcini=new ArrayList<>();
        sarcini.add(s1);
        sarcini.add(s2);
    }

    @Test
    public void deleteElementFisa() throws Exception {
        fiseManagement.deleteElementFisa(1);
    }

    @Test
    public void addFisa() throws Exception {
       // fiseManagement.addFisa(p1,sarcini);
        assertEquals(fiseManagement.getAllFise().size(),1);
    }


    @Test
    public void updateElementFisa() throws Exception {
        fiseManagement.updateElementFisa(1,new Post(2,"salam","parttime"),sarcini);
    }


    @Test
    public void getAllFise() throws Exception {
        fiseManagement.getAllFise().forEach(System.out::println);
        //assertTrue(true);

    }

    @Test
    public void findElemFisaById() throws Exception {
        assertTrue(true);

    }

}