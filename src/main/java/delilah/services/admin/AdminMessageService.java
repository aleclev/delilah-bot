package delilah.services.admin;

import delilah.api.rest.dto.DiscordChannelDTO;
import delilah.api.rest.dto.DiscordUserDTO;
import delilah.api.rest.dto.MessageContentDto;
import delilah.domain.exceptions.DelilahException;
import delilah.domain.models.permission.Role;
import delilah.domain.models.user.User;
import delilah.infrastructure.repositories.UserRepository;
import delilah.services.auth.AuthService;
import delilah.services.discord.DiscordUserVerificationService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class AdminMessageService {

    private final DiscordUserVerificationService discordUserVerificationService;

    private final UserRepository userRepository;
    private final JDA jda;
    private final AuthService authService;

    public AdminMessageService(DiscordUserVerificationService discordUserVerificationService, UserRepository userRepository, JDA jda, AuthService authService) {
        this.discordUserVerificationService = discordUserVerificationService;
        this.userRepository = userRepository;
        this.jda = jda;
        this.authService = authService;
    }

    public List<DiscordChannelDTO> getChannels(String accessToken, String guildId) {

        authService.authorizeAdminFromDiscordAccessToken(accessToken);

        return jda.getGuildById(guildId)
                .getChannels(false).stream()
                .map(c -> new DiscordChannelDTO(c.getName(), c.getId()))
                .collect(Collectors.toList());
    }

    public MessageContentDto getMessageContentById(String accessToken, String channelId, String messageId)
            throws ExecutionException, InterruptedException {

        authService.authorizeAdminFromDiscordAccessToken(accessToken);

        String content = jda.getTextChannelById(channelId)
                .retrieveMessageById(messageId)
                .mapToResult().submit().get().get().getContentDisplay();

        MessageContentDto messageContentDto = new MessageContentDto();
        messageContentDto.content = content;

        MessageContentDto dto = new MessageContentDto();
        dto.content = content;

        return dto;
    }



    public void updateExistingMessage(String accessToken, String channelId, String messageId, String content) {

        authService.authorizeAdminFromDiscordAccessToken(accessToken);
        jda.getTextChannelById(channelId).editMessageById(messageId, content).queue();
    }

    public URI createMessage(String accessToken, String channelId, String content) throws ExecutionException, InterruptedException, URISyntaxException {

        authService.authorizeAdminFromDiscordAccessToken(accessToken);

        Message message = jda
                .getChannelById(TextChannel.class, channelId)
                .sendMessage(content).mapToResult().submit().get().get();

        return new URI(message.getJumpUrl());
    }
}
