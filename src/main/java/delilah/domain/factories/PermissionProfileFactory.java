package delilah.domain.factories;

import delilah.domain.models.permission.PermissionProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PermissionProfileFactory {

    public PermissionProfile createDefault() {
        return new PermissionProfile(new ArrayList<>());
    }
}
