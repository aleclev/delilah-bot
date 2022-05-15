package repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repositories.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class HibernateRepository<T> implements Repository<T> {
    protected SessionFactory sessionFactory;
    private Class<T> entityClass;

    public HibernateRepository(SessionFactory sessionFactory) {
        this.entityClass = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T getById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(this.entityClass, id);
        }
    }

    @Override
    public void add(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove(T entity) {

    }

    @Override
    public List<T> getAll() {
        return null;
    }
}
