package repositories.hibernate;

import models.dictionnary.Dictionary;
import org.hibernate.SessionFactory;

public class DictionaryHibernateRepository extends HibernateRepository<Dictionary> {
    public DictionaryHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
