package delilah.client.commands.lookingForGroup;

import delilah.client.commands.AbstractSlashSubcommand;
import delilah.client.commands.commandPayloads.LFGCreateGroupCommandPayload;
import delilah.client.commands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.lookingForGroup.LookingForGroupService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@ConsumesPayload(type = LFGCreateGroupCommandPayload.class)
public class LFGCreateGroupCommand extends AbstractSlashSubcommand {

    @Autowired
    private LookingForGroupService lfgService;

    public LFGCreateGroupCommand() {
        super("create", "Create an event group.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {
        var p = (LFGCreateGroupCommandPayload)payload;

        lfgService.createEventGroup(commandEvent.getUser().getId(), p.activity, p.description, p.maxSize);
    }
}
