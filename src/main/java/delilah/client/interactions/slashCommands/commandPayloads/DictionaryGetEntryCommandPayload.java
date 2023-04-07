package delilah.client.interactions.slashCommands.commandPayloads;

import delilah.client.interactions.slashCommands.payloadProcessing.annotations.Argument;

public class DictionaryGetEntryCommandPayload {

    @Argument(description = "The word you want to look for.", autocomplete = true)
    public String word;

    @Argument(optional = true, name = "dictionary_id", description = "The Id of the dictionary you want to look into.")
    public String dictionaryId;
}
