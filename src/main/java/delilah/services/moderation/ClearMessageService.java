package delilah.services.moderation;

import delilah.domain.exceptions.DelilahException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class ClearMessageService {

    public void clearMessagesFromChannel(Member member, TextChannel channel, Integer amount) {

        if (amount > 50)
            throw new DelilahException("Amount may not be above 50.");
        if (!member.hasPermission(Permission.MESSAGE_MANAGE))
            throw new DelilahException("Missing permissions.");

        channel.getHistory().retrievePast(amount).queue(messages -> messages.forEach(m -> m.delete().queue()));
    }
}
