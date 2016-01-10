package lu.cortex.spi;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilien Charton on 10/01/16.
 */
public class ServiceSpiDefault implements ServiceSpi {

    private String name = StringUtils.EMPTY;
    private List<String> references = new ArrayList<>();

    public ServiceSpiDefault(String name) {
        this.setName(name);
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("service={");
        buffer.append(" name:" + name);
        buffer.append(", references:" + references);
        return buffer.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addReference(String reference) {
        getReferences().add(reference);
    }

    @Override
    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }
}
