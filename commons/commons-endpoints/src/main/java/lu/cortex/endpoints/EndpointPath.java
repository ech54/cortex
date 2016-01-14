package lu.cortex.endpoints;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public enum EndpointPath {

    DOMAIN(0),
    PROCESS(1),
    REF(3);
    public static final String SEPARATOR = ":";
    int position = 0;

    EndpointPath(int position) {
        this.position = position;
    }
    public int getPosition() { return this.position;}

    public static String getDomain(String path) {
        return getValue(path, DOMAIN);
    }

    public static String getProcess(String path) {
        return getValue(path, PROCESS);
    }

    public static String getRef(String path) {
        return getValue(path, REF);
    }

    protected static String getValue(final String path, final EndpointPath partOfPath) {
        if (!StringUtils.contains(path, SEPARATOR)) {
            new EndpointException("Path is not valid. Must follow pattern '<domain>:<processName>'");
        }
        if (StringUtils.contains(path, SEPARATOR+SEPARATOR)) {
            return StringUtils.EMPTY;
        }
        final List<String> partOfPaths = Arrays.asList(StringUtils.split(path, EndpointPath.SEPARATOR));
        if (partOfPaths.size() < partOfPath.getPosition()) {
            return StringUtils.EMPTY;
        }
        return partOfPaths.get(partOfPath.getPosition());
    }
    public static String toString(final Endpoint endpoint) { return endpoint.getSystemAlias() + ":" + endpoint.getPath();}
    public static String buildPath(String domain) {
        return domain;
    }
    public static String buildPath(String domain, String process) {
        return domain + ":" + process;
    }
    public static String buildPath(String domain, String process, String ref) {
        return domain + ":" + process + ":" + ref;
    }
}
