package br.com.rnery;

import br.com.rnery.dao.ClientDAO;
import br.com.rnery.dao.generic.IGenericDAO;
import br.com.rnery.domain.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

public class ClientTest {
    private ClientDAO dao;
    private Client c;

    @Before
    public void init() {
        dao = new ClientDAO();
        c = new Client();
        c.setName("Rafael");
        c.setCpf(11122233345L);
        c.setAddress("Rua Bananeira");
        c.setCity("São Paulo");
        c.setState("SP");
        c.setTel(333222111L);
        c.setAddressNum(395L);
    }

    @Test
    public void registerTest() throws Exception {
        Integer countRegister = dao.register(c);
        Assert.assertEquals(1, (int) countRegister);

        Client cDB = dao.getOne(c.getCpf(), c);
        Assert.assertNotNull(cDB);
        Assert.assertEquals(c.getCpf(), cDB.getCpf());

        Integer countDelete = dao.delete(c);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test(expected = PSQLException.class)
    public void resgisterSameUniqueIdentifierTest() throws Exception {
        Integer countRegister = dao.register(c);
        Assert.assertEquals(1, (int) countRegister);

        Client c2 = new Client();
        c.setName("Rafael");
        c.setCpf(11122233345L);
        c.setAddress("Rua Bananeira");
        c.setCity("São Paulo");
        c.setState("SP");
        c.setTel(333222111L);
        c.setAddressNum(395L);

        Integer countRegister2 = dao.register(c);
        Assert.assertEquals(1, (int) countRegister2);

        Integer countDelete = dao.delete(c);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test
    public void getOneTest() throws Exception {
        Integer countRegister = dao.register(c);
        Assert.assertEquals(1, (int) countRegister);

        Client cDB = dao.getOne(c.getCpf(), c);
        Assert.assertNotNull(cDB);
        Assert.assertEquals(c.getCpf(), cDB.getCpf());

        Integer countDelete = dao.delete(c);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test
    public void getAllTest() {

    }
}
