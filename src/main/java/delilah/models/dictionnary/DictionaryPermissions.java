package delilah.models.dictionnary;

import delilah.models.user.User;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class DictionaryPermissions {

    private String DictionaryPermissionsId;

    private User owner;

    Dictionary dictionary;


    private List<DictionaryPermissionOverride> permissionOverrides;


    private Map<DictionaryAction, DictionaryPermissionScope> defaultPermissions;

    public DictionaryPermissions(
            String dictionaryPermissionsId, User owner,
            List<DictionaryPermissionOverride> permissionOverrides,
            TreeMap<DictionaryAction,
                    DictionaryPermissionScope> defaultPermissions) {
        DictionaryPermissionsId = dictionaryPermissionsId;

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