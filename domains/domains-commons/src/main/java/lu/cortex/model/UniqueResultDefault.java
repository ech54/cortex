package lu.cortex.model;

/**
 * The unique result which contains a <code>R</code> result.
 * @param <R> The type of result.
 */
public class UniqueResultDefault<R> implements UniqueResult {

    // The result encapsulated by the unique result wrapper.
    R result;

    /**
     * The default constructor.
     * @param result
     */
    public UniqueResultDefault(final R result){
        this.result = result;
    }

    /**
     * Accessor in reading on the result contains into the wrapper.
     * @return The result value.
     */
    @Override
    public R getResult() {
        return result;
    }

    /**
     * Indicates that a result is contained into the wrapper.
     * @return <code>true</code> if a result is contained
     *  into the wrapper, else <code>false</code>.
     */
    @Override
    public boolean isExist() {
        return result != null;
    }
}
