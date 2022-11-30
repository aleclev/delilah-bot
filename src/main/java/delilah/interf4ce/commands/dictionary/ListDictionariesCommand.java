package delilah.interf4ce.commands.dictionary;

import delilah.interf4ce.commands.AbstractSlashCommand;
import delilah.infrastructure.repositories.DictionaryRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListDictionariesCommand extends AbstractSlashCommand {

    @Autowired
    DictionaryRepository dictionaryRepository;

    public ListDictionariesCommand() {
        super("dictionary-list", "Get a list of your owned dictionaries.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

    }
}
