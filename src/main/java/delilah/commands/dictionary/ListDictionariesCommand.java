package delilah.commands.dictionary;

import delilah.commands.AbstractSlashCommand;
import delilah.repositories.DictionaryRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class ListDictionariesCommand extends AbstractSlashCommand {

    @Autowired
    DictionaryRepository dictionaryRepository;

    public ListDictionariesCommand() {
        super("dictionary-list", "Get a list of your owned dictionaries.");
    }

    @Override
    protected void execute(SlashCommandInteractionEvent commandEvent) {

    }
}
