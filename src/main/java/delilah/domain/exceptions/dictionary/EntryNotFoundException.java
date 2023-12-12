package delilah.domain.exceptions.dictionary;

import delilah.domain.exceptions.DelilahException;

public class EntryNotFoundException extends DelilahException {
    public EntryNotFoundException(String message) {
        super(message);
    }
}
