package br.com.rnery.dao;

import br.com.rnery.dao.generic.GenericDAO;
import br.com.rnery.domain.Client;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO extends GenericDAO<Client> {

    public ClientDAO() {
        this.ref = Client.class;
    }

    @Override
    protected void addInsertParams(PreparedStatement stm, Client entity) throws SQLException {
        stm.setString(1, entity.getName());
        stm.setLong(2, entity.getCpf());
        stm.setLong(3, entity.getTel());
        stm.setString(4, entity.getAddress());
        stm.setLong(5, entity.getAddressNum());
        stm.setString(6, entity.getCity());
        stm.setString(7, entity.getState());
    }

    @Override
    protected String getUpdateSQL() {
        StringBuilder st = new StringBuilder();

        st.append("UPDATE tb_client ");
        st.append("SET NAME = ?, CPF = ?, TEL = ?, ADDRESS = ?, ADDRESS_NUM = ?, CITY = ?, STATE = ? ");
        st.append("WHERE ID = ?");

        return st.toString();
    }

    @Override
    protected void addUpdateParams(PreparedStatement stm, Client entity) throws SQLException {
        stm.setString(1, entity.getName());
        stm.setLong(2, entity.getCpf());
        stm.setLong(3, entity.getTel());
        stm.setString(4, entity.getAddress());
        stm.setLong(5, entity.getAddressNum());
        stm.setString(6, entity.getCity());
        stm.setString(7, entity.getState());
        stm.setLong(8, entity.getId());
    }
}
