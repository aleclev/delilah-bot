package delilah.util;

import net.dv8tion.jda.api.utils.TimeFormat;

import java.time.Instant;

public class DiscordTime {

    private final Instant timestamp;
    private final TimeFormat timeFormat;

    public DiscordTime(Instant timestamp, TimeFormat timeFormat) {
        this.timestamp = timestamp;
        this.timeFormat = timeFormat;
    }

    @Override
    public String toString() {
        return "<t:" + timestamp.getEpochSecond() + ":" + timeFormat.getStyle() + ">";
    }
}
