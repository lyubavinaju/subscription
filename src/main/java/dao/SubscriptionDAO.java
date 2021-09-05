package dao;

import entities.Publication;
import entities.Subscriber;
import entities.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO extends AbstractDAO<Subscription> {
    private static final String GET_ONE = "SELECT * FROM SUBSCRIPTION WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM SUBSCRIPTION";
    private static final String INSERT_ONE = "INSERT INTO SUBSCRIPTION (SUBSCRIBER,PUBLICATION) VALUES" +
            "(?, ?)";
    private static final String DELETE_ONE = "DELETE FROM SUBSCRIPTION WHERE ID = ?";


    private final SubscriberDAO subscriberDAO;
    private final PublicationDAO publicationDAO;

    public SubscriptionDAO(Connection connection) {
        super(connection);
        subscriberDAO = new SubscriberDAO(connection);
        publicationDAO = new PublicationDAO(connection);
    }

    @Override
    public Subscription findById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ONE)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long subscriptionId = resultSet.getLong("ID");
            long subscriberId = resultSet.getLong("SUBSCRIBER");
            long publicationId = resultSet.getLong("PUBLICATION");
            Subscriber subscriber = subscriberDAO.findById(subscriberId);
            Publication publication = publicationDAO.findById(publicationId);
            return new Subscription(subscriptionId, publication, subscriber);

        }
    }

    @Override
    public List<Subscription> getAll() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_ALL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long subscriptionId = resultSet.getLong("ID");
                long subscriberId = resultSet.getLong("SUBSCRIBER");
                long publicationId = resultSet.getLong("PUBLICATION");
                Subscriber subscriber = subscriberDAO.findById(subscriberId);
                Publication publication = publicationDAO.findById(publicationId);
                Subscription subscription = new Subscription(subscriptionId, publication, subscriber);
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    @Override
    public void insert(Subscription subscription) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ONE)) {
            preparedStatement.setLong(1, subscription.getSubscriber().getId());
            preparedStatement.setLong(2, subscription.getPublication().getId());
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
