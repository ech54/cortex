package lu.cortex.domain.policy.api;

import java.util.List;

import lu.cortex.model.Link;

/**
 * Model designs an <code>Insurance Cover</code>
 *  describing the covered risks for which financial
 *  plan will be processed.
 */
public interface Cover {
    /**
     * Accessor in reading on the covered risks
     *  taken in charge by the cover.
     * @return The risks.
     */
    List<Risk> getCoveredRisk();

    /**
     * Accessor in writing on the covered risks
     *  taken in charge by the cover.
     * @param risks The risks.
     */
    void setCoveredRisk(final List<Risk> risks);

}
