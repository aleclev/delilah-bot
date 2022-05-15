package repositories;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    T getById(String id);
    void add(T entity);
    void remove(T entity);
    List<T> getAll();
}
