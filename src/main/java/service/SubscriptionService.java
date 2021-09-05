package service;

import dao.AbstractDAO;
import entities.Publication;
import entities.Subscriber;
import entities.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubscriptionService extends AbstractService<Subscription> {


    private final SubscriberService subscriberService;
    private final PublicationService publicationService;

    public SubscriptionService(AbstractDAO<Subscription> accessObject,
                               SubscriberService subscriberService,
                               PublicationService publicationService) {
        super(accessObject);
        this.subscriberService = subscriberService;
        this.publicationService = publicationService;
    }


    public Optional<Exception> insert(long subId, long pubId) {
        try {
            Optional<Subscriber> maybeSubscriber = subscriberService.findById(subId);
            if (maybeSubscriber.isPresent()) {
                Subscriber subscriber = maybeSubscriber.get();
                Optional<Publication> maybePublication = publicationService.findById(pubId);
                if (maybePublication.isPresent()) {
                    Publication publication = maybePublication.get();
                    getAccessObject().insert(new Subscription(null, publication, subscriber));
                    return Optional.empty();
                } else {
                    return Optional.of(new Exception("Cannot get publication"));

                }

            } else {
                return Optional.of(new Exception("Cannot get subscriber"));

            }
        } catch (Exception e) {
            return Optional.of(e);
        }

    }


    public Optional<List<Publication>> getPublications(long subId) {
        Optional<List<Subscription>> all = getAll();
        if (all.isEmpty()) {
            return Optional.empty();
        }
        List<Publication> list = all.get().stream().filter(subscription ->
                subscription
                        .getSubscriber()
                        .getId()
                        .equals(subId))
                .map(Subscription::getPublication)
                .collect(Collectors.toList());
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list);
    }

    public Optional<List<Subscriber>> getSubscribers(long pubId) {
        Optional<List<Subscription>> all = getAll();
        if (all.isEmpty()) {
            return Optional.empty();
        }
        List<Subscriber> list = all.get().stream().filter(subscription ->
                subscription
                        .getPublication()
                        .getId()
                        .equals(pubId))
                .map(Subscription::getSubscriber)
                .collect(Collectors.toList());
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list);
    }
}
