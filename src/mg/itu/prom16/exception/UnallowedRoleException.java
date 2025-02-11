package mg.itu.prom16.exception;

public class UnallowedRoleException extends RuntimeException {
    public UnallowedRoleException() {
        super("User doesn't have authorization to perform this action");
    }
}
