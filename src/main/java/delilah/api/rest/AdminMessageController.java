package delilah.api.rest;

import delilah.api.rest.dto.DiscordChannelDTO;
import delilah.api.rest.dto.MessageContentDto;
import delilah.domain.exceptions.DelilahException;
import delilah.services.admin.AdminMessageService;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/message")
public class AdminMessageController {

    private final AdminMessageService adminMessageService;
    private final String sherpaRunGuildId;

    public AdminMessageController(AdminMessageService adminMessageService,
                                  @Value("${delilah.discord.test-server.id}") String sherpaRunGuildId) {
        this.adminMessageService = adminMessageService;
        this.sherpaRunGuildId = sherpaRunGuildId;
    }

    @GetMapping("channels")
    public ResponseEntity<List<DiscordChannelDTO>> getChannels(@RequestHeader("access_token") String accessToken) {

        try {
            return ResponseEntity.ok(adminMessageService.getChannels(accessToken, sherpaRunGuildId));
        } catch (DelilahException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<MessageContentDto> getMessageContentById(@RequestHeader("access_token") String accessToken,
                                                                   @PathParam("channelId") String channelId,
                                                                   @PathParam("messageId") String messageId)
            throws ExecutionException, InterruptedException {

        try {
            return ResponseEntity.ok(adminMessageService.getMessageContentById(accessToken, channelId, messageId));
        } catch (DelilahException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping
    public ResponseEntity updateExistingMessage(@RequestHeader("access_token") String accessToken,
                                                @PathParam("channelId") String channelId,
                                                @PathParam("messageId") String messageId,
                                                @RequestBody MessageContentDto messageContentDto) {

        try {
            adminMessageService.updateExistingMessage(accessToken, channelId, messageId, messageContentDto.content);
            return ResponseEntity.ok().build();
        } catch (DelilahException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity createMessage(@RequestHeader("access_token") String accessToken,
                                        @PathParam("channelId") String channelId,
                                        @RequestBody MessageContentDto messageContentDto
    ) throws ExecutionException, InterruptedException, URISyntaxException {

        try {
            return ResponseEntity
                    .created(adminMessageService.createMessage(accessToken, channelId, messageContentDto.content)).build();
        } catch (DelilahException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
