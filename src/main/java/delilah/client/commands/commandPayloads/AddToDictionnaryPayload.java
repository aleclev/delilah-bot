package delilah.client.commands.commandPayloads;

import delilah.client.commands.payloadProcessing.annotations.Argument;

public class AddToDictionnaryPayload {

    @Argument(description = "Word you will use to access the definition.")
    public String word;

    @Argument(description = "The definition you want to associate to the ")
    public String definition;

    @Argument(optional = true, name = "dictionary_id", description = "Id of the dictionary you want to modify.")
    public String dictionaryId;
}
