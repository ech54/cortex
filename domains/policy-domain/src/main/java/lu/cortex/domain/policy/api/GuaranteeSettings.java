package lu.cortex.domain.policy.api;

import lu.cortex.model.DataModel;
import lu.cortex.model.Link;

public interface GuaranteeSettings extends DataModel {
    /**
     * Accessor in reading on the guarantee.
     * @return The cover.
     */
    Guarantee getGuarantee();

    /**
     * Accessor in writing on the guarantee.
     * @param guarantee The guarantee.
     */
    void setGuarantee(final Guarantee guarantee);

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
