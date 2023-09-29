package delilah.api.rest;

import delilah.api.rest.dto.DiscordUserDTO;
import delilah.api.rest.dto.GroupEventCreationDTO;
import delilah.services.discord.DiscordUserVerificationService;
import delilah.services.groupEvent.GroupEventService;
import org.springframework.web.bind.annotation.*;

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
    public Object createEvent(@RequestHeader("access_token") String accessToken,
                              @RequestBody GroupEventCreationDTO groupEventCreationDTO)
            throws ExecutionException, InterruptedException {

        DiscordUserDTO discordUserDTO = discordUserVerificationService.verifyUserFromAccessToken(accessToken);
        return groupEventService.createEventGroup(discordUserDTO.id, groupEventCreationDTO.activity,
                groupEventCreationDTO.activity, groupEventCreationDTO.maxSize, groupEventCreationDTO.startTime);
    }
}
