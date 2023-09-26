package delilah.api.rest;

import delilah.api.rest.dto.DiscordTokenDto;
import delilah.services.discord.DiscordAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final DiscordAuthService discordAuthService;

    public AuthController(DiscordAuthService discordAuthService) {
        this.discordAuthService = discordAuthService;
    }

    @GetMapping("/discordToken")
    public DiscordTokenDto getAccessTokenFromCode(@RequestParam String code) {
        return discordAuthService.getAccessTokenFromCode(code);
    }
}
