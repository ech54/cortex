package lu.cortex.spi;

import java.util.List;

/**
 * Interface allows to operate on service register
 *  inside the domain.
 */
public interface ServiceSpi {
    /**
     * Accessor in reading on the service spi name.
     * @return The service name.
     */
    String getName();

    /**
     * Method add a new reference name from the
     *  service spi.
     * @param reference A new reference from the service.
     */
    void addReference(final String reference);

    /**
     * Accessor in reading on all references define
     *  at the service SPI.
     * @return The list of reference.
     */
    List<String> getReferences();

}
