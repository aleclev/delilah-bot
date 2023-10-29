package domain.factories;

import delilah.domain.factories.UserFactory;
import delilah.domain.models.dictionnary.Dictionary;
import delilah.domain.models.notification.NotificationProfile;
import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testUtil.DictionaryBuilder;
import testUtil.NotificationProfileBuilder;
import testUtil.PermissionProfileBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {

    private static final String ID = "134567089";
    private DictionaryBuilder dictionaryBuilder;
    private PermissionProfileBuilder permissionProfileBuilder;
    private NotificationProfileBuilder notificationProfileBuilder;

    private UserFactory userFactory;

    @BeforeEach
    public void setup() {
        dictionaryBuilder = new DictionaryBuilder();
        permissionProfileBuilder = new PermissionProfileBuilder();
        notificationProfileBuilder = new NotificationProfileBuilder();

        userFactory = new UserFactory();
    }

    @Test
    public void whenCreatingUser_thenUserIsCreated() {
        Dictionary dictionary = dictionaryBuilder.build();
        PermissionProfile permissionProfile = permissionProfileBuilder.build();
        NotificationProfile notificationProfile = notificationProfileBuilder.build();

        User user = userFactory.createUser(ID, dictionary, permissionProfile, notificationProfile);

        assertThat(user).isNotNull();
    }
}
