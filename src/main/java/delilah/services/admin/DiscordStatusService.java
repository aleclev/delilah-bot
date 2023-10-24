package delilah.services.admin;

import delilah.infrastructure.repositories.ConfigVariable;
import delilah.infrastructure.repositories.ConfigVariableRepository;
import delilah.services.auth.AuthService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DiscordStatusService {

    private final JDA jda;
    private final AuthService authService;

    private final ConfigVariableRepository configVariableRepository;

    public DiscordStatusService(JDA jda, AuthService authService, ConfigVariableRepository configVariableRepository) {
        this.jda = jda;
        this.authService = authService;
        this.configVariableRepository = configVariableRepository;
    }

    public void setCustomStatus(String accessToken, String status) {

        authService.authorizeAdminFromDiscordAccessToken(accessToken);

        configVariableRepository.save(new ConfigVariable("customStatus", status));

        jda.getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, status));
    }

    @Scheduled(fixedDelay = 60_000)
    public void reloadStatus() {

        ConfigVariable customStatus = configVariableRepository.findById("customStatus");

        if (Objects.nonNull(customStatus))
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, customStatus.getValue()));;
    }
}
