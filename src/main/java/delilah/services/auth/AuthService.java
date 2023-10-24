package delilah.services.auth;

import delilah.api.rest.dto.DiscordUserDTO;
import delilah.domain.exceptions.DelilahException;
import delilah.domain.models.permission.Role;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import delilah.services.discord.DiscordUserVerificationService;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthService {

    private final DiscordUserVerificationService discordUserVerificationService;
    private final UserRepository userRepository;

    public AuthService(DiscordUserVerificationService discordUserVerificationService, UserRepository userRepository) {
        this.discordUserVerificationService = discordUserVerificationService;
        this.userRepository = userRepository;
    }

     public void authorizeAdminFromDiscordAccessToken(String accessToken) throws DelilahException {

        DiscordUserDTO discordUserDTO = discordUserVerificationService.verifyUserFromAccessToken(accessToken);

        if (Objects.isNull(discordUserDTO))
            throw new DelilahException("Unauthorized");

        User user = userRepository.findByDiscordId(discordUserDTO.id);

        if (!user.hasRole(Role.ADMIN))
            throw new DelilahException("Unauthorized");
    }
}
