package repositories;

import models.user.User;

public interface UserRepository extends Repository<User> {
    User getByDiscordId(Long discordId);
}
