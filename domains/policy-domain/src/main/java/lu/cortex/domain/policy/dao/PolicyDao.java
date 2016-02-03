package lu.cortex.domain.policy.dao;

import javax.persistence.Query;

import lu.cortex.spi.CommonDao;

public class PolicyDao<Policy> extends CommonDao<Policy> {

    public PolicyDao() {
    }

    @Override
    protected Query buildGetByReferenceQuery(final String reference) {
        final Query getQuery = getEntityManager().createQuery(
                "SELECT p FROM PolicyDefault p WHERE p.businessReference = :searchingKey");
        getQuery.setParameter("searchingKey", reference);
        return getQuery;
    }
}
