package lu.cortex.endpoints;


public class EndpointException extends RuntimeException{
    public EndpointException() {super();}
    public EndpointException(final String msg) {super(msg);}
    public EndpointException(final String msg, final Throwable cause) {super(msg, cause);}
}
