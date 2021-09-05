package dao;

import entities.Subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDAO extends AbstractDAO<Subscriber> {
    private static final String GET_ONE = "SELECT * FROM SUBSCRIBER WHERE ID=?";
    private static final String GET_ALL = "SELECT * FROM SUBSCRIBER";
    private static final String INSERT_ONE = "INSERT INTO SUBSCRIBER (S_NAME) VALUES(?)";
    private static final String DELETE_ONE = "DELETE FROM SUBSCRIBER WHERE ID = ?";

    public SubscriberDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Subscriber findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long subId = resultSet.getLong("ID");
            String subName = resultSet.getString("S_NAME");
            return new Subscriber(subId, subName);

        }

    }

    @Override
    public List<Subscriber> getAll() throws SQLException {
        List<Subscriber> addressList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("S_NAME");
                Subscriber subscriber = new Subscriber(id, name);
                addressList.add(subscriber);
            }
        }
        return addressList;
    }

    @Override
    public void insert(Subscriber subscriber) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ONE)) {
            preparedStatement.setString(1, subscriber.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

}
