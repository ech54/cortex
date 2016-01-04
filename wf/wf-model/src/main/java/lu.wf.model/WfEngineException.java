package lu.wf.model;

public class WfEngineException extends RuntimeException {

    public WfEngineException() {super();}
    public WfEngineException(final String msg) {super(msg);}
    public WfEngineException(final String msg, final Throwable cause) {super(msg, cause);}
}
