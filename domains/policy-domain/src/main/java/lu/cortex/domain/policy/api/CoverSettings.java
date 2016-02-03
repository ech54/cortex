package lu.cortex.domain.policy.api;

import lu.cortex.model.DataModel;
import lu.cortex.model.Link;

public interface CoverSettings extends DataModel {
    /**
     * Accessor in reading on the cover.
     * @return The cover.
     */
    Cover getCover();

    /**
     * Accessor in writing on the cover.
     * @param cover The cover.
     */
    void setCover(final Cover cover);

    /**
     * Accessor in reading on the financial plan
     *  which is processed as foreseen by cover.
     * @return The financial plan.
     */
    Link getFinancialPlan();

    /**
     * Accessor in writing on the financial plan
     *  which is processed as foreseen by cover.
     * @param plan The financial plan.
     */
    void setFinancialPlan(final Link plan);
}
