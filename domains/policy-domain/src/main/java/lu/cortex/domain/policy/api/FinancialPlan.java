package lu.cortex.domain.policy.api;

import lu.cortex.model.Link;

/**
 * Model the financial plan associated to cover.
 * That's mean in which the investment has been invested (portfolio),
 *  and which are the investment settings
 */
public interface FinancialPlan {

    Link getInvestmentPortfolio();

    void setInvestmentPortfolio(final Link portfolio);

    Link getInvestmentSettings();

    void setInvestmentSettings(final Link settings);
}
