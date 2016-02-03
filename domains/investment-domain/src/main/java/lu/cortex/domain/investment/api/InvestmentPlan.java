package lu.cortex.domain.investment.api;

import java.util.List;

import lu.cortex.domain.investment.api.common.InvestmentOwner;
import lu.cortex.domain.investment.api.common.InvestmentTarget;
import lu.cortex.domain.investment.api.definition.FinancialInstrumentDefinition;
import lu.cortex.domain.investment.api.definition.InvestmentServiceDefinition;

public interface InvestmentPlan {

    InvestmentOwner getOwner(final InvestmentOwner owner);

    void setOwner(final InvestmentOwner owner);

    InvestmentTarget getTarget();

    void setTarget(final InvestmentTarget target);

    List<FinancialInstrumentDefinition> getPortfolio();

    void setPortfolio(final List<FinancialInstrumentDefinition> portfolio);

    List<InvestmentServiceDefinition> getServices();

    void setServices(final List<InvestmentServiceDefinition> services);

    List<InvestmentValorization> getValorizations();

    void setValorizations(final List<InvestmentValorization> valorizations);
}
