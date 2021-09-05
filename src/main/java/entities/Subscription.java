package entities;

public class Subscription implements Entity {


    private final Long id;
    private final Publication publication;
    private final Subscriber subscriber;

    public Subscription(Long id, Publication publication, Subscriber subscriber) {
        this.id = id;
        this.publication = publication;
        this.subscriber = subscriber;
    }

    public Long getId() {
        return id;
    }

    public Publication getPublication() {
        return publication;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }
    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", publication=" + publication +
                ", subscriber=" + subscriber +
                '}';
    }
}
