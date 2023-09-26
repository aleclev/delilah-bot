package delilah.client.interactions.slashCommands.lookingForGroup;

import delilah.client.interactions.slashCommands.commandPayloads.LFGCreateGroupCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.autocomplete.AddActivityAutocompleteService;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@ConsumesPayload(type = LFGCreateGroupCommandPayload.class)
public class LFGCreateGroupCommand extends AbstractSlashSubcommand {

    @Autowired
    private LookingForGroupService lfgService;

    @Autowired
    private AddActivityAutocompleteService addActivityAutocompleteService;

    public LFGCreateGroupCommand() {
        super("create", "Create an event group.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        var p = (LFGCreateGroupCommandPayload)payload;

        String messageUrl = lfgService.createEventGroup(commandEvent.getUser().getId(), p.activity, p.description, p.maxSize);

        commandEvent.reply(
                String.format("Group created: %s\nClick `Summon Group` to message members.\nClick `Alert` to notify users who are subscribed to your alerts from you activity.", messageUrl))
                .setEphemeral(true).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        event.replyChoices(addActivityAutocompleteService.getSuggestions(event)).queue();
    }
}
