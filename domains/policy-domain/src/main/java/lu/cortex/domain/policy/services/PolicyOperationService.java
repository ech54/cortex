package lu.cortex.domain.policy.services;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.cortex.domain.policy.api.Policy;
import lu.cortex.domain.policy.dao.PolicyDao;
import lu.cortex.model.Status;
import lu.cortex.model.UniqueResult;
import lu.cortex.spi.CommonOperationServices;

@Component
public class PolicyOperationService implements CommonOperationServices<Policy> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyOperationService.class);

    @Autowired
    private PolicyDao<Policy> dao;

    public PolicyOperationService() { }

    @Override
    public UniqueResult<Policy> getByReference(final String reference) {
        return null;
    }

    @Override
    public boolean isExist(final String reference) {
        LOGGER.info("Policy exist - reference=", reference);
        return getDao().isExist(reference);
    }

    @Override
    public Policy update(final Policy model) {
        Objects.nonNull(model);
        Objects.nonNull(model.getBusinessReference());
        LOGGER.info("Policy update - reference=", model.getBusinessReference());
        if (!getDao().isExist(model.getBusinessReference())) {
            throw new RuntimeException("Can't update the policy model for reference="
                    + model.getBusinessReference());
        }
        final Policy persistent = getDao().get(model.getBusinessReference());
        if (model.getVersion().equals(persistent.getVersion())) {
            throw new RuntimeException("Policy model version is not the last persisted. reference="
                    + persistent.getBusinessReference());
        }
        getDao().update(model);
        return getDao().get(model.getBusinessReference());
    }

    @Override
    public void create(final Policy model) {
        Objects.nonNull(model);
        if (model.getVersion() != null
                && !model.getStatus().equals(Status.NEW)) {
            throw new RuntimeException("Can't persist model. " + model);
        }
        getDao().create(model);
    }

    @Override
    public void delete(final String reference) {
    }

    public PolicyDao<Policy> getDao() {
        return dao;
    }

    public void setDao(PolicyDao<Policy> dao) {
        this.dao = dao;
    }
}
