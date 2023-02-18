package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashCommand;
import delilah.infrastructure.repositories.DictionaryRepository;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ListDictionariesCommand extends AbstractSlashCommand {

    @Autowired
    DictionaryService dictionaryService;

    public ListDictionariesCommand() {
        super("dictionary-list-entries", "Get a list of your owned dictionaries.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

    }
}
