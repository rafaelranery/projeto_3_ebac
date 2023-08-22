package br.com.rnery;

import br.com.rnery.dao.ClientDAO;
import br.com.rnery.dao.generic.IGenericDAO;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
    private ClientDAO dao;

    @Before
    public void init() {
        dao = new ClientDAO();
    }

    @Test
    public void registerTest() {

    }
}
