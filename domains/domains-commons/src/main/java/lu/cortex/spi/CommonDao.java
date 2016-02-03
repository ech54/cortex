package lu.cortex.spi;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public abstract class CommonDao<T> implements CrudDao<T> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean isExist(final String reference) {

        return !buildGetByReferenceQuery(reference).getResultList().isEmpty();
    }

    @Override
    public T get(final String reference) {
        final List results = buildGetByReferenceQuery(reference).getResultList();
        if (results.size() != 1) {
            throw new RuntimeException("Can't provide retrieve model for: " + reference);
        }
        return (T) results.get(0);
    }

    /**
     * Build the custom query responsibles to retrieve all matching results
     *  with reference given in parameter.
     * @param reference The business reference.
     * @return the query to get matching model.
     */
    protected abstract Query buildGetByReferenceQuery(final String reference);

    @Override
    public void create(final T model) {
        getEntityManager().persist(model);
    }

    @Override
    public void delete(final String reference) {
        T model = get(reference);
        getEntityManager().remove(model);
    }

    @Override
    public void update(final T model) {
        getEntityManager().persist(model);
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }
}
