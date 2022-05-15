package models.dictionnary;

import models.user.User;

import java.util.List;
import java.util.TreeMap;

public class DictionaryPermissions {
    private User owner;
    private List<DictionaryPermissionOverride> permissionOverrides;
    private TreeMap<DictionaryAction, DictionaryPermissionScope> defaultPermissions;

    public DictionaryPermissions(
            User owner,
            List<DictionaryPermissionOverride> permissionOverrides,
            TreeMap<DictionaryAction,
            DictionaryPermissionScope> defaultPermissions) {

        this.owner = owner;
        this.permissionOverrides = permissionOverrides;
        this.defaultPermissions = defaultPermissions;
    }

    public boolean userActionAuthorized(User user, DictionaryAction action) {
        if (user.equals(owner)) return true;

        if (defaultPermissions.getOrDefault(action, DictionaryPermissionScope.Owner)
                .equals(DictionaryPermissionScope.Everyone)) return true;

        return permissionOverrides.stream().anyMatch(p -> p.dictionaryAction.equals(action) && p.user.equals(user));
    }
}
