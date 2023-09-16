package delilah.client.interactions.slashCommands.moderation;

import delilah.client.interactions.slashCommands.AbstractSlashSingleCommand;
import delilah.client.interactions.slashCommands.commandPayloads.ClearCommandPayload;
import delilah.client.interactions.slashCommands.payloadProcessing.annotations.ConsumesPayload;
import delilah.services.moderation.ClearMessageService;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@ConsumesPayload(type = ClearCommandPayload.class)
public class ClearMessagesCommand extends AbstractSlashSingleCommand {

    @Autowired
    ClearMessageService clearMessageService;

    public ClearMessagesCommand() {
        super("clear", "Deletes a certain amount of messages.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent commandEvent, Object payload) throws ExecutionException, InterruptedException {

        ClearCommandPayload p = (ClearCommandPayload)payload;
        Integer amount = p.amount;

        commandEvent.deferReply(true).queue();

        TextChannel channel = commandEvent.getChannel().asTextChannel();

        clearMessageService.clearMessagesFromChannel(commandEvent.getMember(), channel, amount);

        commandEvent.getHook().sendMessage("Messages queued for deletion!").queue();
    }

}
