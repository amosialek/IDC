package tai.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Operation forbidden")
public class OperationForbiddenException extends RuntimeException {
    public OperationForbiddenException() {
    }

    public OperationForbiddenException(String message) {
        super(message);
    }
}
