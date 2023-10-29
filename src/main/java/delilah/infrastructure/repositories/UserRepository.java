package delilah.infrastructure.repositories;

import delilah.domain.models.notification.NotificationSubscription;
import delilah.domain.models.user.User;
import java.util.List;

public interface UserRepository extends Repository<User> {

    List<User> findBySubscriptions(List<NotificationSubscription> subscriptions);
}
