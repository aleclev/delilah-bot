package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashParentCommand;
import delilah.client.commands.payloadProcessing.SlashCommandPayloadExtractor;
import org.springframework.stereotype.Component;


@Component
public class DictionaryParentCommand extends AbstractSlashParentCommand {

    public DictionaryParentCommand(DictionaryGetEntryCommand dictionaryGetEntryCommand,
                                   AddToDictionaryCommand addToDictionaryCommand,
                                   RemoveFromDictionaryCommand removeFromDictionaryCommand,
                                   SlashCommandPayloadExtractor extractor) {

        super("word", "Commands for the dictionary module.", extractor);
        this.registerSubcommand(dictionaryGetEntryCommand);
        this.registerSubcommand(addToDictionaryCommand);
        this.registerSubcommand(removeFromDictionaryCommand);
    }
}
