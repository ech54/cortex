package lu.cortex.registry.container.service;

/**
 * Default response send by registry lu.cortex.registry.container.cache
 *  operation.
 */
public interface RegistryResponse {
    /**
     * Accessor in the registry response status.
     * @return The status.
     */
    String getStatus();

    /**
     * Accessor in reading on the registry response description.
     * @return The description.
     */
    String getDescription();

    /**
     * Indicates if the response content is present.
     * @return <code>true</code> if the body is existing,
     *  else <code>false</code>.
     */
    boolean isContentExisting();

    /**
     * Accessor in reading on the body of the registry.
     * @return The registry body response.
     */
    RegistryBodyResponse getBody();
}
