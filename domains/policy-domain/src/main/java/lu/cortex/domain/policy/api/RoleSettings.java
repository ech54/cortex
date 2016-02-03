package lu.cortex.domain.policy.api;

import lu.cortex.model.Link;

public interface RoleSettings {

    Link getUser();

    void setUser(final Link user);

}
