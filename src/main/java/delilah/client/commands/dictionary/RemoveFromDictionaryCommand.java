package delilah.client.commands.dictionary;

import delilah.client.commands.AbstractSlashCommand;
import delilah.client.commands.commandPayloads.DictionaryGetEntryCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.DictionaryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@ConsumesPayload(type = DictionaryGetEntryCommandPayload.class)
public class RemoveFromDictionaryCommand extends AbstractSlashCommand {

    @Autowired
    private DictionaryService dictionaryService;

    public RemoveFromDictionaryCommand(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) {

        DictionaryGetEntryCommandPayload p = (DictionaryGetEntryCommandPayload) payload;
        String discordId = commandEvent.getUser().getId();
        String word = p.word;

        if (dictionaryService.removeDictionaryEntryForUser(discordId, word) == null) commandEvent.reply("Error! Word not found.").queue();
        else commandEvent.reply("Word deleted.").queue();
    }
}
