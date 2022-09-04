package delilah.repositories;

import java.util.List;

public interface Repository<T> {
    T getById(String id);
    void add(T entity);
    void remove(T entity);
    List<T> getAll();
}
