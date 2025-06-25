package org.example.deal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Getter
@Setter
public class SimpleErrorResponse implements ErrorResponse {
    private String error;
    private String message;

    public SimpleErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return null;
    }

    @Override
    public ProblemDetail getBody() {
        return null;
    }
}