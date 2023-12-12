package delilah.client.interactions.slashCommands.lookingForGroup;

import delilah.client.interactions.slashCommands.commandPayloads.GroupEventCreateCommandPayload;
import delilah.client.interactions.slashCommands.AbstractSlashSubcommand;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.domain.exceptions.DelilahException;
import delilah.services.autocomplete.AddActivityAutocompleteService;
import delilah.services.groupEvent.GroupEventService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
@ConsumesPayload(type = GroupEventCreateCommandPayload.class)
public class LFGCreateGroupCommand extends AbstractSlashSubcommand {

    private final GroupEventService lfgService;
    private final AddActivityAutocompleteService addActivityAutocompleteService;
    private final Clock clock;


    public LFGCreateGroupCommand(GroupEventService lfgService, AddActivityAutocompleteService addActivityAutocompleteService, Clock clock) {
        super("create", "Create an event group.");
        this.lfgService = lfgService;
        this.addActivityAutocompleteService = addActivityAutocompleteService;
        this.clock = clock;
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        var p = (GroupEventCreateCommandPayload)payload;

        Instant startTime = clock.instant();

        if (Objects.nonNull(p.startTime)) {
            try {
                startTime = Instant.parse(p.startTime);
            }
            catch (Exception e) {
                throw new DelilahException("Could not parse the given datetime.");
            }
        }

        String messageUrl = lfgService.createEventGroup(commandEvent.getUser().getId(), p.activity, p.description, p.maxSize, startTime);

        commandEvent.reply(
                String.format("Group created: %s\nClick `Summon Group` to message members.\nClick `Alert` to notify users who are subscribed to your alerts from you activity.", messageUrl))
                .setEphemeral(true).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteraction event) {

        event.replyChoices(addActivityAutocompleteService.getSuggestions(event)).queue();
    }
}
