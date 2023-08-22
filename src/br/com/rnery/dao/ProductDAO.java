package br.com.rnery.dao;

import br.com.rnery.dao.generic.GenericDAO;
import br.com.rnery.domain.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends GenericDAO<Product> {

    public ProductDAO() {
        this.ref = Product.class;
    }

    @Override
    protected String getUpdateSQL() {
        StringBuilder st = new StringBuilder();
        st.append("UPDATE tb_product ");
        st.append("SET NAME = ?, DESCRIPTION = ?, PRICE = ?, CODE = ? ");
        st.append("WHERE ID = ?;");

        return st.toString();
    }

    @Override
    protected void addInsertParams(PreparedStatement stm, Product entity) throws SQLException {
        stm.setString(1, entity.getName());
        stm.setString(2, entity.getDescription());
        stm.setDouble(3, entity.getPrice());
        stm.setLong(4, entity.getCode());
    }

    @Override
    protected void addUpdateParams(PreparedStatement stm, Product entity) throws SQLException {
        stm.setString(1, entity.getName());
        stm.setString(2, entity.getDescription());
        stm.setDouble(3, entity.getPrice());
        stm.setLong(4, entity.getCode());
        stm.setLong(5, entity.getId());
    }
}
