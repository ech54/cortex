package lu.cortex.domain.policy.api;

import java.util.List;

import lu.cortex.model.Link;

/**
 * Model designs an <code>Insurance Guarantee</code>
 *  describing the covered risks for which financial
 *  plan will be processed.
 */
public interface Guarantee {
    /**
     * Accessor in reading on the guarantee risks
     *  taken in charge by the cover.
     * @return The risks.
     */
    List<Risk> getGuaranteeRisks();

    /**
     * Accessor in writing on the guarantee risks
     *  taken in charge by the cover.
     * @param risks The risks.
     */
    void setGuaranteeRisks(final List<Risk> risks);

}
