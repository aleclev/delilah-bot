package commands.registration;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import factories.UserFactory;
import models.user.User;
import repositories.UserRepository;

public class RegisterDiscordCommand extends Command {
    UserRepository userRepository;
    UserFactory userFactory;

    public RegisterDiscordCommand(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;

        this.name = "register";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        Long discordId = commandEvent.getAuthor().getIdLong();

        User user = userFactory.createUser(discordId);
        userRepository.add(user);

        commandEvent.replyFormatted("Registration successful! Your unique id is %s", user.getUserId());
    }
}
