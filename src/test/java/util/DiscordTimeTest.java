package util;

import delilah.util.DiscordTime;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscordTimeTest {

    private static TimeFormat TIMEFORMAT = TimeFormat.TIME_SHORT;
    private static Instant INSTANT = Instant.ofEpochSecond(123);
    private static String EXPECTED_STRING = "<t:123:t>";

    DiscordTime discordTime;

    @BeforeEach
    public void setup() {
        discordTime = new DiscordTime(INSTANT, TIMEFORMAT);
    }

    @Test
    public void givenValidDiscordTime_whenConvertingToString_thenConvertedStringIsValid() {

        String result = discordTime.toString();

        assertEquals(EXPECTED_STRING, result);

    }
}
