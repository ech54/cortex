/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.configuration;

import java.util.List;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.model.DomainDefinition;

public interface DomainDefinitionManager {

    Object executeAsyncProcess(final Endpoint endpoint, Object...args);

    List<DomainDefinition> getDomainDefinitions();
}
