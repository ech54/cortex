package lu.cortex.endpoints;

public class EndpointDefault implements Endpoint {

    private String systemAlias;
    private String path;

    public EndpointDefault(final String alias, final String path) {
        this.systemAlias = alias;
        this.path = path;
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
