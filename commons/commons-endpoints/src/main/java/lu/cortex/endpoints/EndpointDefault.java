package lu.cortex.endpoints;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("endpoint")
public class EndpointDefault implements Endpoint {

    @JsonProperty("alias")
    private String systemAlias;

    @JsonProperty("path")
    private String path;

    @JsonCreator
    public EndpointDefault(@JsonProperty("alias") final String alias,
                           @JsonProperty("path") final String path) {
        this.systemAlias = alias;
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("endpoint={");
        buffer.append(" alias:"+systemAlias);
        buffer.append(", path:"+path);
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public String getSystemAlias() {
        return systemAlias;
    }

    public void setSystemAlias(String systemAlias) {
        this.systemAlias = systemAlias;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
