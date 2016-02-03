package lu.cortex.domain.investment.api.common;

import java.util.List;

import lu.cortex.domain.investment.api.definition.InterestDefinition;
import lu.cortex.model.Link;

public interface FinancialCharacteristics {

    void setFeeDefinitions(final List<Link> fees);

    List<Link> getFeeDefinitions();

    void setInterestDefinitions(final List<InterestDefinition> interests);

    List<InterestDefinition> getInterestDefinitions();
}
