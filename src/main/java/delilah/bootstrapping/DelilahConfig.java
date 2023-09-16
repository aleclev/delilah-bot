package delilah.bootstrapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;
import java.util.Random;

@Configuration
@EnableScheduling
public class DelilahConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public Random random(@Autowired Clock clock) {
        return new Random(clock.instant().getEpochSecond());
    }
}
