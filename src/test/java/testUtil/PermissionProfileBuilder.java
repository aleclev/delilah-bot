package testUtil;

import delilah.domain.models.permission.PermissionProfile;
import delilah.domain.models.permission.Role;

import java.util.List;

public class PermissionProfileBuilder {

    List<Role> roles;

    public PermissionProfile build() {
        return new PermissionProfile(roles);
    }
}
