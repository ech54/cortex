package lu.engine.core.utils;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import lu.engine.core.model.ProcessStep;
import lu.cortex.endpoints.EndpointPath;
import lu.wf.model.WfEngineException;

public class ProcessStepBuilder {

    private static ProcessStep instance = null;

    public ProcessStepBuilder() {
        this.instance = new ProcessStep();
    }

    public ProcessStepBuilder addProcessPath(final String path) {
        if (!StringUtils.contains(path, EndpointPath.SEPARATOR)) {
            new WfEngineException("Path is not valid. Must follow pattern '<domain>:<processName>'");
        }
        final List<String> subPaths = Arrays.asList(StringUtils.split(path, EndpointPath.SEPARATOR));
        instance.setDomain(subPaths.get(EndpointPath.DOMAIN.getPosition()));
        instance.setProcessName(subPaths.get(EndpointPath.PROCESS.getPosition()));
        return this;
    }

    public ProcessStepBuilder addActivityName(final String name) {
        instance.setStepName(name);
        return this;
    }


    public ProcessStep build() {
        return this.instance;
    }

}
