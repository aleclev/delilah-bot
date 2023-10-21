package delilah.api.rest;

import delilah.api.rest.dto.DiscordChannelDTO;
import delilah.api.rest.dto.MessageContentDto;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/message")
public class AdminMessageController {

    private final JDA jda;
    private final String sherpaRunGuildId;

    public AdminMessageController(JDA jda,
                                  @Value("${delilah.discord.test-server.id}") String sherpaRunGuildId) {
        this.jda = jda;
        this.sherpaRunGuildId = sherpaRunGuildId;
    }

    @GetMapping("channels")
    public List<DiscordChannelDTO> getChannels() {

        return jda.getGuildById(sherpaRunGuildId)
                .getChannels(false).stream()
                .map(c -> new DiscordChannelDTO(c.getName(), c.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("")
    public String getMessageContentById(@PathParam("channelId") String channelId,
                                        @PathParam("messageId") String messageId) throws ExecutionException, InterruptedException {

        return jda.getTextChannelById(channelId).retrieveMessageById(messageId).mapToResult().submit().get().get().getContentDisplay();
    }

    @PatchMapping()
    public ResponseEntity updateExistingMessage(@PathParam("channelId") String channelId,
                                                @PathParam("messageId") String messageId,
                                                @RequestBody MessageContentDto messageContentDto) {

        jda.getTextChannelById(channelId).editMessageById(messageId, messageContentDto.content).queue();

        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity createMessage(@PathParam("channelId") String channelId, @RequestBody MessageContentDto messageContentDto
    ) throws ExecutionException, InterruptedException, URISyntaxException {

        Message message = jda
                .getChannelById(TextChannel.class, channelId)
                .sendMessage(messageContentDto.content).mapToResult().submit().get().get();

        return ResponseEntity.created(new URI(message.getJumpUrl())).build();
    }
}
