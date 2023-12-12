package delilah.domain.exceptions.user;

import delilah.domain.exceptions.DelilahException;

public class UserNotFoundException extends DelilahException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
