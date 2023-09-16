package delilah.api.rest;

import delilah.api.rest.dto.DiscordUserDTO;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.services.DictionaryService;
import delilah.services.discord.DiscordUserVerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final DiscordUserVerificationService discordUserVerificationService;

    public DictionaryController(DictionaryService dictionaryService, DiscordUserVerificationService discordUserVerificationService) {
        this.dictionaryService = dictionaryService;
        this.discordUserVerificationService = discordUserVerificationService;
    }

    @GetMapping()
    public Dictionary getDictionaryEntries(@RequestHeader("access_token") String accessToken) {
        DiscordUserDTO userDTO = discordUserVerificationService.verifyUserFromAccessToken(accessToken);

        return dictionaryService.getDictionaryForUser(userDTO.id);
    }
}
