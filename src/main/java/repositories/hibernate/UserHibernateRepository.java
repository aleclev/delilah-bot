package repositories.hibernate;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import models.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repositories.UserRepository;

public class UserHibernateRepository extends HibernateRepository<User> implements UserRepository {

    public UserHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User getByDiscordId(Long discordId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("discordId"), discordId));

            Query<User> query = session.createQuery(cr);
            return query.stream().findFirst().get();
        }
    }
}
