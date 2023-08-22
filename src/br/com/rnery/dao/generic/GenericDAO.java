package br.com.rnery.dao.generic;

import br.com.rnery.annotations.SQLInsertCMD;
import br.com.rnery.annotations.SQLTable;
import br.com.rnery.domain.Persistent;
import br.com.rnery.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public abstract class GenericDAO<T extends Persistent> implements IGenericDAO<T> {
    @Override
    public Integer register(T entity) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnetion();
            String SQL_CMD = getRegisterSQL(entity);
            stm = connection.prepareStatement(SQL_CMD);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(connection, stm, null);
        }
    }

    @Override
    public Integer update(T entity) throws Exception {
        return null;
    }

    @Override
    public T getOne(Long key) throws Exception {
        return null;
    }

    @Override
    public Set<T> getAll() throws Exception {
        return null;
    }

    @Override
    public Integer delete() throws Exception {
        return null;
    }

    private String getRegisterSQL(T entity) {
        StringBuilder st = new StringBuilder();
        String tableName = entity.getClass().getAnnotation(SQLTable.class).value();
        String[] insertFields = entity.getClass().getAnnotation(SQLInsertCMD.class).value();

        st.append("INSERT INTO").append(" ");
        st.append(tableName).append(" ");
        st.append(insertFields[0]).append(" ");
        st.append("VALUES").append(" ");
        st.append(insertFields[0]);

        return st.toString();
    }

    private void closeConnections(Connection connection, PreparedStatement stm, ResultSet resultSet) throws SQLException {
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
        if(stm != null && !stm.isClosed()) {
            stm.close();
        }
        if(resultSet != null && !resultSet.isClosed()) {
            resultSet.close();
        }
    }
}
