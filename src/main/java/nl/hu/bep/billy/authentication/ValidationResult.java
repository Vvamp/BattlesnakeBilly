package nl.hu.bep.billy.authentication;

public class ValidationResult {
    private final ValidationStatus status;
    private final String details;

    public ValidationResult(ValidationStatus status, String details) {
        this.status = status;
        this.details = details;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

}
