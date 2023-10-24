package delilah.api.rest;

import delilah.services.admin.DiscordStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/status")
public class DiscordStatusController {

    private final DiscordStatusService discordStatusService;

    public DiscordStatusController(DiscordStatusService discordStatusService) {
        this.discordStatusService = discordStatusService;
    }

    @PostMapping
    public ResponseEntity setCustomStatus(@RequestHeader("access_token") String accessToken, @RequestBody String status) {

        discordStatusService.setCustomStatus(accessToken, status);

        return ResponseEntity.ok().build();
    }
}
