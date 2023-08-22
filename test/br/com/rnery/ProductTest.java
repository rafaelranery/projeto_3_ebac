package br.com.rnery;

import br.com.rnery.dao.ProductDAO;
import br.com.rnery.domain.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public class ProductTest {
    ProductDAO dao;
    Product p;

    @Before
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        dao = new ProductDAO();
        p = new Product();
        p.setName("Product Name 1");
        p.setDescription("Product 1 Description");
        p.setPrice(2.5D);
        p.setCode(123L);
    }

    @Test
    public void registerTest() throws Exception {
        Integer countRegister = dao.register(p);
        Assert.assertEquals(1, (int) countRegister);

        Integer countDelete = dao.delete(p);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test(expected = PSQLException.class)
    public void sameUniqueKeyTest() throws Exception {
        Product p2 = new Product();

        p2.setCode(p.getCode());
        p2.setPrice(p.getPrice());
        p2.setDescription(p.getDescription());
        p2.setName(p.getName());

        List<Product> list = List.of(p, p2);

        try {
            for(Product product : list) {
                dao.register(product);
            }
        } finally {
            Integer countDelete = dao.delete(p);
            Assert.assertEquals(1, (int) countDelete);
        }
    }

    @Test
    public void getOneTest() throws Exception {
        dao.register(p);

        Product pDB = dao.getOne(p.getCode());
        Assert.assertNotNull(pDB);
        Assert.assertEquals(p.getCode(), pDB.getCode());
        Assert.assertEquals(p.getName(), pDB.getName());

        Integer countDelete = dao.delete(p);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test
    public void getAllTest() {

    }

    @Test
    public void updateTest() {

    }
}
