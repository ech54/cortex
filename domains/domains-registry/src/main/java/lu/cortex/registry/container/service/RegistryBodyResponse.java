package lu.cortex.registry.container.service;

/**
 * Default marker used to transport the content
 *  of the response.
 */
public interface RegistryBodyResponse {
    /**
     * Accessor in reading on the content of the response body.
     * @return The body content.
     */
    String getContent();
}
