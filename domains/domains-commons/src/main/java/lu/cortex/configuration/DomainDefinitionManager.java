/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.configuration;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.spi.DomainDefinition;

public interface DomainDefinitionManager {

    Object executeAsyncProcess(final Endpoint endpoint, Object...args);

    DomainDefinition getDomainDefinition();
}
