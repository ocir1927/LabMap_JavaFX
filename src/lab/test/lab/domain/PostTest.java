package lab.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 05.11.2016
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class PostTest {
    Post p1,p2;
    @Before()
    public void setUp() throws Exception {
       p1=new Post(2,"Sef","fulltime")  ;
       p2=new Post(1,"Sclav","fulltime")  ;
    }

    @Test
    public void testCopy() throws Exception {
        p1.copy(p2);
        assertEquals(p1.getDenumire(),p2.getDenumire());
        assertEquals(p1.getTip(),p2.getTip());
        assertNotSame(p1.getId(),p2.getId());
    }

    @Test
    public void testGetDenumire() throws Exception {
        assertEquals("Sef",p1.getDenumire());
    }

}
