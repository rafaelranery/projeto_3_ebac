package br.com.rnery.dao.generic;

import br.com.rnery.annotations.*;
import br.com.rnery.domain.Persistent;
import br.com.rnery.factory.ConnectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class GenericDAO<T extends Persistent> implements IGenericDAO<T> {
    // TODO
    // utilizar ref para não precisar passar a entity em métodos de busca e delete.
    protected Class<? extends Persistent> ref;

    @Override
    public Integer register(T entity) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnetion();
            String SQL_CMD = getInsertSQL(entity);
            stm = connection.prepareStatement(SQL_CMD);
            addInsertParams(stm, entity);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(connection, stm);
        }
    }


    @Override
    public Integer update(T entity) throws Exception {
        Connection c = null;
        PreparedStatement stm = null;

        try {
            c = ConnectionFactory.getConnetion();
            String SQL_CMD = getUpdateSQL();
            stm = c.prepareStatement(SQL_CMD);
            addUpdateParams(stm, entity);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(c, stm);
        }
    }



    @Override
    public T getOne(Long key) throws Exception {
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            c = ConnectionFactory.getConnetion();
            String SQL_CMD = getSelectOneSQL(this.ref);
            stm = c.prepareStatement(SQL_CMD);
            addSelectOneParams(stm, key);
            rs = stm.executeQuery();

            if(rs.next()) {
                Class<? extends Persistent> eClass = this.ref;
                Constructor<? extends Persistent> constructor = eClass.getConstructor();
                Field[] cols = eClass.getDeclaredFields();
                Method[] methods = eClass.getMethods();
                T entityDB = (T) constructor.newInstance();

                for (Field f : cols) {
                    if (f.isAnnotationPresent(SQLColumn.class)) {
                        String colName = f.getAnnotation(SQLColumn.class).SQLColumn();
                        String setMethodName = f.getAnnotation(SQLColumn.class).setJavaName();
                        Method setMethod = null;
                        Class<?>[] parameterTypes = null;

                        for (Method m : methods) {
                            if(m.getName().equals(setMethodName)) {
                                setMethod = m;
                                parameterTypes = setMethod.getParameterTypes();
                                break;
                            }
                        }

                        if (parameterTypes[0] == Long.class) {
                            setMethod.invoke(entityDB, rs.getLong(colName));
                        } else if (parameterTypes[0] == String.class) {
                            setMethod.invoke(entityDB, rs.getString(colName));
                        } else if (parameterTypes[0] == Double.class) {
                            setMethod.invoke(entityDB, rs.getDouble(colName));
                        }
                    }
                }
                return entityDB;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(c, stm, rs);
        }
        return null;
    }

    @Override
    public Set<T> getAll() throws Exception {
        Connection c = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Set<T> setDB = new HashSet<>();

        try {
            c = ConnectionFactory.getConnetion();
            String SQL_CDM = "SELECT * FROM " + this.ref.getAnnotation(SQLTable.class).value();
            stm = c.prepareStatement(SQL_CDM);
            rs = stm.executeQuery();

            while (rs.next()) {
                Class<? extends Persistent> eClass = this.ref;
                Constructor<? extends Persistent> constructor = eClass.getConstructor();
                Field[] cols = eClass.getDeclaredFields();
                Method[] methods = eClass.getMethods();
                T entityDB = (T) constructor.newInstance();

                for (Field f : cols) {
                    if (f.isAnnotationPresent(SQLColumn.class)) {
                        String colName = f.getAnnotation(SQLColumn.class).SQLColumn();
                        String setMethodName = f.getAnnotation(SQLColumn.class).setJavaName();
                        Method setMethod = null;
                        Class<?>[] parameterTypes = null;

                        for (Method m : methods) {
                            if(m.getName().equals(setMethodName)) {
                                setMethod = m;
                                parameterTypes = setMethod.getParameterTypes();
                                break;
                            }
                        }

                        if (parameterTypes[0] == Long.class) {
                            setMethod.invoke(entityDB, rs.getLong(colName));
                        } else if (parameterTypes[0] == String.class) {
                            setMethod.invoke(entityDB, rs.getString(colName));
                        }
                    }
                }
                setDB.add(entityDB);
            }
            return setDB;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(c, stm, rs);
        }
    }

    @Override
    public Integer delete(T entity) throws Exception {
        Connection c = null;
        PreparedStatement stm = null;
        
        try {
            c = ConnectionFactory.getConnetion();
            String SQL_CMD = getDeleteSQL(entity);
            stm = c.prepareStatement(SQL_CMD);
            addDeleteParams(stm, entity);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections(c, stm);
        }
    }

    private String getDeleteSQL(T entity) throws InvocationTargetException, IllegalAccessException {
        StringBuilder st = new StringBuilder();
        Field[] cols = entity.getClass().getDeclaredFields();
        String tableName = entity.getClass().getAnnotation(SQLTable.class).value();
        String uniqueCol = null;

        for (Field f : cols) {
            if(f.isAnnotationPresent(SQLUniqueCol.class)) {
                uniqueCol = f.getName();
                break;
            }
        }

        st.append("DELETE FROM ");
        st.append(tableName);
        st.append(" WHERE ");
        st.append(uniqueCol);
        st.append(" = ?;");

        return st.toString();
    }

    private String getInsertSQL(T entity) {
        StringBuilder st = new StringBuilder();
        String tableName = entity.getClass().getAnnotation(SQLTable.class).value();
        String[] insertFields = entity.getClass().getAnnotation(SQLInsertCMD.class).value();

        st.append("INSERT INTO").append(" ");
        st.append(tableName).append(" ");
        st.append(insertFields[0]).append(" ");
        st.append("VALUES").append(" ");
        st.append(insertFields[1]);

        return st.toString();
    }

    private String getSelectOneSQL(Class entity) {
        StringBuilder st = new StringBuilder();
        String uniqueCol = null;
        String tableName = this.ref.getAnnotation(SQLTable.class).value();
        Field[] cols = this.ref.getDeclaredFields();

        for (Field f : cols) {
            if(f.isAnnotationPresent(SQLUniqueCol.class)) {
                uniqueCol = f.getName();
                break;
            }
        }

        st.append("SELECT * FROM ");
        st.append(tableName);
        st.append(" WHERE ");
        st.append(uniqueCol);
        st.append(" = ?;");

        return st.toString();
    }

    protected abstract String getUpdateSQL();

    protected abstract void addInsertParams(PreparedStatement stm, T entity) throws SQLException;
    protected abstract void addUpdateParams(PreparedStatement stm, T entity) throws SQLException;

    protected void addDeleteParams(PreparedStatement stm, T entity ) throws SQLException, InvocationTargetException, IllegalAccessException {
        Method searchMethod = null;
        Method[] methods = entity.getClass().getDeclaredMethods();

        for (Method m : methods) {
            if(m.isAnnotationPresent(SQLUnique.class)) {
                searchMethod = m;
                break;
            }
        }

        if (searchMethod != null) {
            stm.setLong(1, (Long) searchMethod.invoke(entity));
        }
    };

    private void addSelectOneParams(PreparedStatement stm, Long key) throws InvocationTargetException, IllegalAccessException, SQLException {
        stm.setLong(1, key);
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

    private void closeConnections(Connection connection, PreparedStatement stm) throws SQLException {
        closeConnections(connection, stm, null);
    }
}
