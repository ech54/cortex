package lu.cortex.domain.investment.api.definition;

import lu.cortex.domain.investment.api.common.FinancialInstrument;
import lu.cortex.domain.investment.api.common.InvestmentSplit;

public interface FinancialInstrumentDefinition {

    InvestmentSplit getSplit();

    void setSplit(final InvestmentSplit split);

    FinancialInstrument getFinancialInstrument();

    void setFinancialInstrument(final FinancialInstrument instument);

    InterestDefinition getInterest();

    void setInterest(final InterestDefinition interest);
}
