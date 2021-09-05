package dao;

import entities.Publication;
import entities.Subscriber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicationDAO extends AbstractDAO<Publication> {

    private static final String GET_ONE = "SELECT * FROM PUBLICATION WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM PUBLICATION";
    private static final String INSERT_ONE = "INSERT INTO PUBLICATION (P_NAME) VALUES(?)";
    private static final String DELETE_ONE = "DELETE FROM PUBLICATION WHERE ID = ?";


    public PublicationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Publication findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            long subId = resultSet.getLong("ID");
            String subName = resultSet.getString("P_NAME");
            return new Publication(subId, subName);

        }
    }

    @Override
    public List<Publication> getAll() throws SQLException {
        List<Publication> publicationList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("P_NAME");
                Publication publication = new Publication(id, name);
                publicationList.add(publication);
            }
        }
        return publicationList;
    }

    @Override
    public void insert(Publication publication) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ONE)) {
            preparedStatement.setString(1, publication.getName());
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
