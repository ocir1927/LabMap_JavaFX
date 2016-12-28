package lab.repository;

import lab.domain.Post;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 07.11.2016
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryTest {
    Repository<Post,Integer> repo;
    @Before
    public void setUp() throws Exception {
        repo=new Repository<Post,Integer>();
        repo.add(new Post(1,"dad","dsadas"));
        repo.add(new Post(2,"dadsad","dsdsadasadas"));
    }

    @Test
    public void testAdd() throws Exception {
        assertEquals(repo.getAll().size(),2);
    }

    @Test
    public void testDelete() throws Exception {
        repo.delete(2);
        assertEquals(repo.getAll().size(),1);
    }

    @Test
    public void testUpdate() throws Exception {
        repo.update(new Post(2,"zugrav","fulltime"));
        assertEquals(repo.getAll().get(1).getDenumire(),"zugrav");

    }

    @Test
    public void testFindById() throws Exception {
        Post pp=repo.findById(1);
        assertEquals(pp.getDenumire(),"dad");
    }
}
