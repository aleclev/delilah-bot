package delilah.client.interactions.slashCommands.dictionary;

import delilah.client.interactions.slashCommands.AbstractSlashParentCommand;
import delilah.client.interactions.slashCommands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;

@Component
public class DictionaryParentCommand extends AbstractSlashParentCommand {

    public DictionaryParentCommand(DictionaryGetEntryCommand dictionaryGetEntryCommand,
                                   AddToDictionaryCommand addToDictionaryCommand,
                                   RemoveFromDictionaryCommand removeFromDictionaryCommand,
                                   DictionaryListCommand dictionaryListCommand,
                                   SlashCommandPayloadExtractor extractor) {

        super("word", "Commands for the dictionary module.", extractor);
        this.registerSubcommand(dictionaryGetEntryCommand);
        this.registerSubcommand(addToDictionaryCommand);
        this.registerSubcommand(removeFromDictionaryCommand);
        this.registerSubcommand(dictionaryListCommand);
    }
}
