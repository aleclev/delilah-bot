package repositories.hibernate;

import com.speedment.jpastreamer.application.JPAStreamer;
import models.dictionnary.Dictionary;
import org.hibernate.SessionFactory;

public class DictionaryHibernateRepository extends HibernateRepository<Dictionary> {
    public DictionaryHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
