package lu.cortex.spi;

public class ResultEndpoint {

    private String message;

    private ResultStatus status;

    public ResultEndpoint(){

    }

    public ResultEndpoint(final ResultStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
