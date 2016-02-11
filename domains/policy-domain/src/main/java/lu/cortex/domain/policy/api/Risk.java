package lu.cortex.domain.policy.api;

public interface Risk {

    String getName();

    void setName(final String name);

    RiskType getType();

    void setType(final RiskType type);

    String getDescription();

    void setDescription(final String description);
}
