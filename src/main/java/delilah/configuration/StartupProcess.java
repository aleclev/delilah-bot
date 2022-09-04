package delilah.configuration;

import delilah.commands.AbstractSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StartupProcess implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    JDA jda;

    @Autowired
    List<AbstractSlashCommand> commands;

    @Value("${delilah.phase}")
    String phase;

    @Value("${delilah.discord.test-server.id}")
    String testServerId;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (phase.equals("dev")) devStartup();
        else if (phase.equals("prod")) prodStartup();
    }

    private void devStartup() {
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Guild testGuild = jda.getGuildById(testServerId);

        testGuild.updateCommands().addCommands(commands).queue();
    }

    private void updateCommand(Command toUpdate, List<Command> result) {

    }

    private void prodStartup() {

    }
}
