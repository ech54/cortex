package lu.cortex.spi;

/**
 * Common CRUD operations to handle <code>Model</code>
 *  at domain layer.
 * @param <T> The type of model.
 */
public interface CrudDao<T> {
    /**
     * Indicates if a model matches with the given
     *  business reference.
     * @param reference The reference.
     * @return <code>true</code> if the model is existing,
     *  else <code>false</code>.
     */
    boolean isExist(final String reference);

    /**
     * Get the corresponding model based on the reference
     *  given in parameter.
     * @param reference The reference.
     * @return The model matching with business reference.
     */
    T get(final String reference);

    /**
     * Persist a new model on storage support.
     * @param model The model to persist.
     */
    void create(final T model);

    /**
     * Update an existing model on the storage support.
     * @param model The model to update.
     */
    void update(final T model);

    /**
     * Delete an existing model based on its business reference.
     * @param reference The business reference to delete.
     */
    void delete(final String reference);
}
