package service;

import dao.AbstractDAO;
import entities.Subscriber;

public class SubscriberService extends AbstractService<Subscriber> {

    public SubscriberService(AbstractDAO<Subscriber> accessObject) {
        super(accessObject);
    }


}
