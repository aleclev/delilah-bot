package delilah.util;

import org.springframework.beans.factory.annotation.Value;

public class GroupEventUtil {

    public static Integer MAX_MINUTE_INACTIVITY;

    public GroupEventUtil(@Value("${delilah.lfg.group.max_inactivity}") String inactivity) {

        MAX_MINUTE_INACTIVITY = Integer.parseInt(inactivity);
    }
}
