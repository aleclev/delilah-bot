package delilah.domain.exceptions.groupEvent;

import delilah.domain.exceptions.DelilahException;

public class InvalidGroupEventSizeException extends DelilahException {

    public InvalidGroupEventSizeException(String message) {
        super(message);
    }
}
