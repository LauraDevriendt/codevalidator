package learning.com.codevalidator.models;

public class ErrorMessage {
    private final String status;
    private final String message;

    public ErrorMessage() {
        status = null;
        message = null;
    }

    public ErrorMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
