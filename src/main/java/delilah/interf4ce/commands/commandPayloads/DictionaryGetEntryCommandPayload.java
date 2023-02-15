package delilah.interf4ce.commands.commandPayloads;

import delilah.interf4ce.commands.payloadProcessing.annotations.Argument;

public class DictionaryGetEntryCommandPayload {

    @Argument(description = "The word you want to look for.")
    public String word;

    @Argument(optional = true, name = "dictionary_id", description = "The Id of the dictionary you want to look into.")
    public String dictionaryId;
}
