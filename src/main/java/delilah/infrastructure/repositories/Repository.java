package delilah.infrastructure.repositories;

import java.util.List;

public interface Repository<T> {
    void save(T object);

    T findById(Object id);

    List<T> findAll();
}
