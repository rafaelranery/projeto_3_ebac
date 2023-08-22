package br.com.rnery;

import br.com.rnery.dao.ClientDAO;
import br.com.rnery.dao.generic.IGenericDAO;
import br.com.rnery.domain.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientTest {
    private ClientDAO dao;
    private Client c;

    @Before
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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

        Client cDB = dao.getOne(c.getCpf());
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

        try {
            Integer countRegister2 = dao.register(c);
            Assert.assertEquals(1, (int) countRegister2);
        } finally {
            Integer countDelete = dao.delete(c);
            Assert.assertEquals(1, (int) countDelete);
        }
    }

    @Test
    public void getOneTest() throws Exception {
        Integer countRegister = dao.register(c);
        Assert.assertEquals(1, (int) countRegister);

        Client cDB = dao.getOne(c.getCpf());
        Assert.assertNotNull(cDB);
        Assert.assertEquals(c.getCpf(), cDB.getCpf());

        Integer countDelete = dao.delete(c);
        Assert.assertEquals(1, (int) countDelete);
    }

    @Test
    public void getAllTest() throws Exception {
        Client c2 = new Client();
        c2.setName("Rafael");
        c2.setCpf(123L);
        c2.setAddress("Rua Bananeira");
        c2.setCity("São Paulo");
        c2.setState("SP");
        c2.setTel(333222111L);
        c2.setAddressNum(395L);

        Client c3 = new Client();
        c3.setName("Rafael");
        c3.setCpf(1234L);
        c3.setAddress("Rua Bananeira");
        c3.setCity("São Paulo");
        c3.setState("SP");
        c3.setTel(333222111L);
        c3.setAddressNum(395L);

        List<Client> listToRegister = List.of(c, c2, c3);
        Integer countRegister = 0;
        for (Client client : listToRegister) {
            Integer res = dao.register(client);
            countRegister += res;
        }
        Assert.assertEquals(3, (int) countRegister);

        Set<Client> setDB = dao.getAll();
        Assert.assertEquals(3, setDB.size());

        Integer countDelete = 0;
        for (Client cDB : setDB) {
            Integer res = dao.delete(cDB);
            countDelete += res;
        }
        Assert.assertEquals(3, (int) countDelete);
    }

    @Test
    public void updateTest() throws Exception {
        Integer countRegister = dao.register(c);
        Assert.assertEquals(1, (int) countRegister);

        Client cDB = dao.getOne(c.getCpf());
        Assert.assertNotNull(cDB);

        cDB.setName("Rafael Updated");
        cDB.setCpf(1L);
        cDB.setAddress("Rua Bananeira Updated");
        cDB.setCity("Rio de Janeiro Updated");
        cDB.setState("RJ Updated");
        cDB.setTel(10L);
        cDB.setAddressNum(10L);

        Integer countUpdated = dao.update(cDB);
        Assert.assertEquals(1, (int) countUpdated);

        Client cDBUpdated = dao.getOne(cDB.getCpf());
        Assert.assertNotNull(cDBUpdated);

        Assert.assertEquals(cDB.getCpf(), cDBUpdated.getCpf());
        Assert.assertEquals(cDB.getName(), cDBUpdated.getName());
        Assert.assertEquals(cDB.getAddress(), cDBUpdated.getAddress());
        Assert.assertEquals(cDB.getCity(), cDBUpdated.getCity());
        Assert.assertEquals(cDB.getTel(), cDBUpdated.getTel());
        Assert.assertEquals(cDB.getState(), cDBUpdated.getState());
        Assert.assertEquals(cDB.getAddressNum(), cDBUpdated.getAddressNum());
        Assert.assertEquals(cDB.getId(), cDBUpdated.getId());

        Integer countDelete = dao.delete(cDBUpdated);
        Assert.assertEquals(1, (int) countDelete);
    }
}
