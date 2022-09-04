package delilah.factories;

import delilah.models.dictionnary.DictionaryPermissionOverride;
import delilah.models.dictionnary.DictionaryPermissions;
import delilah.models.user.User;

import java.util.*;

public class DictionaryPermissionsFactory {
    public DictionaryPermissions createDefault(User owner) {
        List<DictionaryPermissionOverride> overrideList = new ArrayList<DictionaryPermissionOverride>();
        TreeMap defaultPermissions = new TreeMap();
        String dictionaryPermissionsId = UUID.randomUUID().toString();
        DictionaryPermissions dictionaryPermissions = new DictionaryPermissions(dictionaryPermissionsId, owner, overrideList, defaultPermissions);

        return new DictionaryPermissions(dictionaryPermissionsId, owner, overrideList, defaultPermissions);
    }
}
