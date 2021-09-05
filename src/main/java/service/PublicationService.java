package service;

import dao.AbstractDAO;
import entities.Publication;

public class PublicationService extends AbstractService<Publication> {
    public PublicationService(AbstractDAO<Publication> accessObject) {
        super(accessObject);
    }


}
