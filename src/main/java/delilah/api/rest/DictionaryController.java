package delilah.api.rest;

import delilah.api.rest.dto.CreateDictionaryEntryDTO;
import delilah.api.rest.dto.DiscordUserDTO;
import delilah.domain.models.dictionnary.DictionaryEntry;
import delilah.services.DictionaryService;
import delilah.services.discord.DiscordUserVerificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final DiscordUserVerificationService discordUserVerificationService;

    public DictionaryController(DictionaryService dictionaryService, DiscordUserVerificationService discordUserVerificationService) {
        this.dictionaryService = dictionaryService;
        this.discordUserVerificationService = discordUserVerificationService;
    }

    @PostMapping("/entry")
    public void createEntry(@RequestHeader("access_token") String accessToken, @RequestBody CreateDictionaryEntryDTO createDictionaryEntryDTO) {
        DiscordUserDTO discordUserDto = discordUserVerificationService.verifyUserFromAccessToken(accessToken);

        dictionaryService.addToUserDictionary(discordUserDto.id, createDictionaryEntryDTO.word, createDictionaryEntryDTO.definition);
    }

    @CrossOrigin
    @DeleteMapping("/entry")
    public void deleteWord(@RequestHeader("access_token") String accessToken, @RequestParam() String word) {
        DiscordUserDTO discordUserDto = discordUserVerificationService.verifyUserFromAccessToken(accessToken);

        dictionaryService.removeDictionaryEntryForUser(discordUserDto.id, word);
    }
}
