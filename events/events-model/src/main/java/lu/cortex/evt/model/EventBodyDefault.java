package lu.cortex.evt.model;

public class EventBodyDefault implements EventBody {

    private String payload;

    public EventBodyDefault() {
    }

    @Override
    public String getPaylodAsString() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
