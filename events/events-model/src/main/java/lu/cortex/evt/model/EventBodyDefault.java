package lu.cortex.evt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("event-body")
public class EventBodyDefault implements EventBody {

    @JsonProperty("payload")
    private String payload;

    public EventBodyDefault() {
    }

    @JsonIgnore
    @Override
    public String getPaylodAsString() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    public String getPayload() {
        return this.payload;
    }
}
