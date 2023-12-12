package delilah.domain.exceptions.dictionary;

import delilah.domain.exceptions.DelilahException;

public class DuplicateEntryException extends DelilahException {

    public DuplicateEntryException(String message) {
        super(message);
    }
}
