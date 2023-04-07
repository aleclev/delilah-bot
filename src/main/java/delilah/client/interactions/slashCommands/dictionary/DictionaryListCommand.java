package delilah.client.interactions.slashCommands.dictionary;

import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class DictionaryListCommand  extends AbstractSlashSubcommand {

    @Autowired
    private DictionaryService dictionaryService;

    public DictionaryListCommand() {
        super("list", "Get a list of all your dictionary entries.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        List<String> words = dictionaryService.getEntriesLikeInputForUser(commandEvent.getUser().getId(), "").stream().map(e -> e.getWord().toString()).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("```");

        words.stream().forEach(w -> sb.append(w).append("\n"));

        sb.append("```");

        commandEvent.reply(sb.toString()).queue();
    }
}
