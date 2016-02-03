package lu.cortex.spi;

import lu.cortex.model.UniqueResult;

/**
 * Common operations for each domain layer to handle <code>T</code>
 *  model.
 * @param <T> The <code>T</code> model.
 */
public interface CommonOperationServices<T> {
    /**
     * Retrieve a <code>T</code> model based on its business reference.
     * @param reference The business reference.
     * @return The unique result provide by the domain layer.
     */
    UniqueResult<T> getByReference(final String reference);

    /**
     * Indicates if model's reference is existing into the domain layer.
     * @param reference The business reference.
     * @return <code>true</code> if the reference is known by domain,
     *  <code>false</code>.
     */
    boolean isExist(final String reference);

    /**
     * Operation manages the creation process of <code>T</code> model.
     * @param model The model.
     */
    void create(final T model);

    /**
     * Operation manages the modification process of <code>T</code> model.
     * @param model The model.
     * @return the updated object.
     */
    T update(final T model);

    /**
     * Operation manages the deletion process of <code>T</code> model.
     * @param reference The business reference.
     */
    void delete(final String reference);
}
