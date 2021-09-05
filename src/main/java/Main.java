import dao.*;
import entities.*;
import service.*;

import utils.JDBCConn;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    private static SubscriberService subscriberService;
    private static PublicationService publicationService;
    private static SubscriptionService subscriptionService;
    private static final String COMMANDS = "Available commands: exit,\n add_user,\n " +
            "add_publication,\n" +
            "add_subscription,\n exit,\n show_users,\n show_publications,\n " +
            "get_publications_by_user,\n" +
            "get_subscribers_by_publication";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = JDBCConn.connection()) {
            try (Statement statement = connection.createStatement()) {
                for (String sql : Utils.getSql("init-ddl.sql")
                ) {
                    statement.addBatch(sql);
                }
                statement.executeBatch();

            }

            subscriberService = new SubscriberService(new SubscriberDAO(connection));
            publicationService =
                    new PublicationService(new PublicationDAO(connection));
            subscriptionService =
                    new SubscriptionService(new SubscriptionDAO(connection), subscriberService, publicationService);
            logic();

            try (Statement statement = connection.createStatement()) {
                for (String sql : Utils.getSql("clean-ddl.sql")
                ) {
                    statement.addBatch(sql);
                }
                statement.executeBatch();
            }
        }
    }


    public static void logic() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Use show_commands to see commands");

            readingLoop:
            while (scanner.hasNext()) {
                String command = scanner.next();
                boolean ok;
                switch (command) {
                    case "exit":
                        break readingLoop;
                    case "show_users":
                        printResults(subscriberService);
                        break;
                    case "show_publications":
                        printResults(publicationService);
                        break;
                    case "add_user":
                        ok = addUser(scanner);
                        if (!ok) {
                            System.out.println("Something wrong.");
                        }
                        break;
                    case "add_publication":
                        ok = addPublication(scanner);
                        if (!ok) {
                            System.out.println("Something wrong.");
                        }
                        break;
                    case "add_subscription":
                        ok = addSubscription(scanner);
                        if (!ok) {
                            System.out.println("Something wrong.");
                        }
                        break;
                    case "get_publications_by_user":
                        Optional<List<Publication>> maybePublications =
                                getPublicationsByUser(scanner);
                        if (maybePublications.isEmpty()) {
                            System.out.println("No publications found.");
                        } else {
                            maybePublications.get().forEach(System.out::println);
                        }
                        break;
                    case "get_subscribers_by_publication":
                        Optional<List<Subscriber>> maybeSubscribers =
                                getSubscribersByPublication(scanner);
                        if (maybeSubscribers.isEmpty()) {
                            System.out.println("No subscribers found.");
                        } else {
                            maybeSubscribers.get().forEach(System.out::println);
                        }
                        break;
                    case "show_commands":
                        System.out.println(COMMANDS);
                        break;
                    default:
                        System.out.println("Unknown commands");

                }
                System.out.println("Use show_commands to see commands");
            }

        }
    }

    private static Optional<List<Publication>> getPublicationsByUser(Scanner scanner) {
        System.out.println("Enter the id:");
        if (scanner.hasNextLong()) {
            long id = scanner.nextLong();
            return subscriptionService.getPublications(id);

        } else {
            return Optional.empty();
        }
    }

    private static Optional<List<Subscriber>> getSubscribersByPublication(Scanner scanner) {
        System.out.println("Enter the id:");
        if (scanner.hasNextLong()) {
            long id = scanner.nextLong();
            return subscriptionService.getSubscribers(id);
        } else {
            return Optional.empty();
        }
    }

    private static boolean addSubscription(Scanner scanner
    ) {
        System.out.println("Choose the subscriber id:");
        if (!scanner.hasNextLong()) {
            return false;
        }
        long subId = scanner.nextLong();
        System.out.println("Choose the publication id:");
        if (!scanner.hasNextLong()) {
            return false;
        }
        long pubId = scanner.nextLong();

        Optional<Exception> optional = subscriptionService.insert(subId, pubId);
        return optional.isEmpty();

    }

    private static boolean addUser(Scanner scanner) {
        System.out.println("Enter the name:");
        if (scanner.hasNext()) {
            String name = scanner.next();
            Optional<Exception> maybeException = subscriberService.insert(
                    new Subscriber(null, name));
            return maybeException.isEmpty();
        } else {
            return false;
        }
    }

    private static boolean addPublication(Scanner scanner) {
        System.out.println("Enter the name:");
        if (scanner.hasNext()) {
            String name = scanner.next();
            Optional<Exception> maybeException = publicationService.insert(new Publication(null, name));
            return maybeException.isEmpty();
        } else {
            return false;
        }
    }

    private static <T> void printResults(AbstractService<T> service) {
        Optional<List<T>> all = service.getAll();
        if (all.isEmpty()) {
            System.out.println("Nothing found");
            return;
        }
        all.get().forEach(System.out::println);

    }
}
