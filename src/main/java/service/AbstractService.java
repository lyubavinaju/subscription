package service;

import dao.AbstractDAO;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> {
    private AbstractDAO<T> accessObject;

    public AbstractService(AbstractDAO<T> accessObject) {
        this.accessObject = accessObject;
    }

    public AbstractDAO<T> getAccessObject() {
        return accessObject;
    }

    public Optional<T> findById(long id) {
        try {
            final T t = getAccessObject().findById(id);
            return Optional.ofNullable(t);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public Optional<List<T>> getAll() {
        try {
            List<T> all = getAccessObject().getAll();
            if (all.isEmpty()) return Optional.empty();
            return Optional.of(all);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Exception> insert(T t) {
        try {
            getAccessObject().insert(t);
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of(e);
        }
    }
}
