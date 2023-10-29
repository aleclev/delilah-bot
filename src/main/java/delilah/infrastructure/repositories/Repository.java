package delilah.infrastructure.repositories;

import java.util.List;

public interface Repository<T> {
    void save(T object);

    T findById(Object id);

    T findById(Object id, boolean searchCache);
    
    void delete(T object);

    List<T> findAll();
}
