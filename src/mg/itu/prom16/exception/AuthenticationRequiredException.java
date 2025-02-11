package mg.itu.prom16.exception;

public class AuthenticationRequiredException extends RuntimeException {
    public AuthenticationRequiredException() {
        super("You need to authenticate to perform this action");
    }
}
