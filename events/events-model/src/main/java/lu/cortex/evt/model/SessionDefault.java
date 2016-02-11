package lu.cortex.evt.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("session")
public class SessionDefault implements Session {

    long id = -1;

    public SessionDefault() {}

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("[session= ");
        buffer.append(" id:" + id + " ]");
        return buffer.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
