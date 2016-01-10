package lu.cortex.spi;

import java.util.List;

/**
 * Created by echarton on 10/01/16.
 */
public interface ServiceSpi {

    String getName();

    void addReference(final String reference);

    List<String> getReferences();

}
