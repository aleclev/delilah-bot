package delilah.domain.models.notification;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NotificationSubscription {

    private String tag;

    public NotificationSubscription(String tag) {
        this.tag = tag;
    }

    public boolean isTag(String tag2) {
        return tag.equals(tag2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationSubscription that = (NotificationSubscription) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}
