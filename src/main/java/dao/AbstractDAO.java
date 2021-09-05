package dao;

import entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T> {

    protected final Connection connection;

    protected AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract T findById(long id) throws SQLException;

    public abstract List<T> getAll() throws SQLException;

    public abstract void insert(T t) throws SQLException;

    public abstract void delete(long id) throws SQLException;
}
