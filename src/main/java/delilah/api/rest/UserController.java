package delilah.api.rest;

import delilah.api.rest.dto.DiscordUserDTO;
import delilah.domain.models.user.User;
import delilah.services.UserService;
import delilah.services.discord.DiscordUserVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DiscordUserVerificationService discordUserVerificationService;

    public UserController(UserService userService, RestTemplate restTemplate, DiscordUserVerificationService discordUserVerificationService) {
        this.userService = userService;
        this.discordUserVerificationService = discordUserVerificationService;
    }

    @GetMapping("/@me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("access_token") String accessToken) {

        try {
            DiscordUserDTO userDTO = discordUserVerificationService.verifyUserFromAccessToken(accessToken);

            if (Objects.isNull(userDTO))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            return ResponseEntity.ok(userService.getOrRegisterUserByDiscordId(userDTO.id));

        } catch(HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
