package lu.cortex.model;


/**
 * The unique result which contains a <code>R</code> result.
 * @param <R> The type of result.
 */
public interface UniqueResult<R> {

    /**
     * Indicates that a result is contained into the wrapper.
     * @return <code>true</code> if a result is contained
     *  into the wrapper, else <code>false</code>.
     */
    boolean isExist();

    /**
     * Accessor in reading on the result contains into the wrapper.
     * @return The result value.
     */
    R getResult();

}