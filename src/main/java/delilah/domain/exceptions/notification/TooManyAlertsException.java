package delilah.domain.exceptions.notification;

import delilah.domain.exceptions.DelilahException;

public class TooManyAlertsException extends DelilahException {

    public TooManyAlertsException(String description) {
        super(description);
    }
}
