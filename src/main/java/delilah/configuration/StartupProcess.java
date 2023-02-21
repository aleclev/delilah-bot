package delilah.configuration;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.AbstractSlashParentCommand;
import delilah.client.commands.AbstractSlashSingleCommand;
import delilah.client.commands.AbstractSlashSubcommand;
import delilah.client.commands.payloadProcessing.SlashCommandArgumentCreator;
import delilah.client.discord.EventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupProcess implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SlashCommandArgumentCreator slashCommandArgumentCreator;
    @Autowired
    JDA jda;

    @Autowired
    EventListener eventListener;

    @Autowired
    List<AbstractSlashSingleCommand> commands;

    @Autowired
    List<AbstractSlashParentCommand> parentCommands;

    @Autowired
    List<AbstractSlashSubcommand> subcommands;

    @Value("${delilah.discord.test-server.id}")
    String testServerId;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        subcommands.forEach(command -> command.addOptions(slashCommandArgumentCreator.extractOptions(command.getClass())));
        commands.forEach(command -> command.addOptions(slashCommandArgumentCreator.extractOptions(command.getClass())));
        parentCommands.forEach(command -> command.addOptions(slashCommandArgumentCreator.extractOptions(command.getClass())));

        jda.addEventListener(eventListener);

        startup();
    }

    private void startup() {
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Guild testGuild = jda.getGuildById(testServerId);

        testGuild.updateCommands().addCommands(commands).addCommands(parentCommands).queue();
    }
}
