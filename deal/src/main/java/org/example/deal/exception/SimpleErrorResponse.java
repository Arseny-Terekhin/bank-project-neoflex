package org.example.deal.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
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
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail detail = ProblemDetail.forStatus(getStatusCode());
        detail.setTitle(error);
        detail.setDetail(message);
        return detail;
    }
}