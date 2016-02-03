package lu.cortex.domain.investment.api.common;

public interface FinancialInstrument {

    String getName();

    void setName(final String name);

    FinancialIntrumentType getType();

    void setType(final FinancialIntrumentType type);

    void setCharacteristics(final FinancialCharacteristics characteristics);

    FinancialCharacteristics getCharacteristics();
}
