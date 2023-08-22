package br.com.rnery;

import br.com.rnery.dao.ProductDAO;
import br.com.rnery.domain.Client;
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
    public void getAllTest() throws Exception {
        Product p2 = new Product();
        p2.setName("Product Name 2");
        p2.setDescription("Product 2 Description");
        p2.setPrice(2.5D);
        p2.setCode(1234L);

        Product p3 = new Product();
        p3.setName("Product Name 3");
        p3.setDescription("Product 3 Description");
        p3.setPrice(2.5D);
        p3.setCode(1235L);

        List<Product> listToRegister = List.of(p, p2, p3);
        Integer countRegister = 0;
        for (Product product : listToRegister) {
            Integer res = dao.register(product);
            countRegister += res;
        }
        Assert.assertEquals(3, (int) countRegister);

        Set<Product> setDB = dao.getAll();
        Assert.assertEquals(3, setDB.size());

        Integer countDelete = 0;
        for (Product pDB : setDB) {
            Integer res = dao.delete(pDB);
            countDelete += res;
        }
        Assert.assertEquals(3, (int) countDelete);
    }

    @Test
    public void updateTest() throws Exception {
        Integer countRegister = dao.register(p);
        Assert.assertEquals(1, (int) countRegister);

        Product pDB = dao.getOne(p.getCode());
        Assert.assertNotNull(pDB);

        pDB.setName("Product Updated");
        pDB.setDescription("Product description updated");
        pDB.setCode(1234567L);
        pDB.setPrice(5.5D);

        Integer countUpdated = dao.update(pDB);
        Assert.assertEquals(1, (int) countUpdated);

        Product pDBUpdated = dao.getOne(pDB.getCode());
        Assert.assertNotNull(pDBUpdated);

        Assert.assertEquals(pDB.getCode(), pDBUpdated.getCode());
        Assert.assertEquals(pDB.getName(), pDBUpdated.getName());
        Assert.assertEquals(pDB.getDescription(), pDBUpdated.getDescription());
        Assert.assertEquals(pDB.getPrice(), pDBUpdated.getPrice());
        Assert.assertEquals(pDB.getId(), pDBUpdated.getId());

        Integer countDelete = dao.delete(pDBUpdated);
        Assert.assertEquals(1, (int) countDelete);
    }
}
