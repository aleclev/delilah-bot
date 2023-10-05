package delilah.api.rest;

import delilah.api.rest.dto.DiscordUserDTO;
import delilah.api.rest.dto.GroupEventCreationDTO;
import delilah.services.discord.DiscordUserVerificationService;
import delilah.services.groupEvent.GroupEventService;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("groupEvent")
public class GroupEventController {

    private final GroupEventService groupEventService;

    private final DiscordUserVerificationService discordUserVerificationService;

    public GroupEventController(GroupEventService groupEventService, DiscordUserVerificationService discordUserVerificationService) {
        this.groupEventService = groupEventService;
        this.discordUserVerificationService = discordUserVerificationService;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestHeader("access_token") String accessToken,
                              @RequestBody GroupEventCreationDTO groupEventCreationDTO)
            throws ExecutionException, InterruptedException, URISyntaxException {

        DiscordUserDTO discordUserDTO = discordUserVerificationService.verifyUserFromAccessToken(accessToken);
        String postUrl = groupEventService.createEventGroup(discordUserDTO.id, groupEventCreationDTO.activity,
                groupEventCreationDTO.description, groupEventCreationDTO.maxSize, groupEventCreationDTO.startTime);

        return ResponseEntity.created(new URI(postUrl)).build();
    }
}
