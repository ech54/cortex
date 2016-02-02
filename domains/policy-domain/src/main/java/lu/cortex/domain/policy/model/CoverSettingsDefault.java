package lu.cortex.domain.policy.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.Cover;
import lu.cortex.model.AbstractDataModel;
import lu.cortex.model.Link;

@Entity
@Table(name="COVER_DEFINITION")
public class CoverSettingsDefault extends AbstractDataModel {

    @Embedded
    Link coverDefinition;

    Cover cover;


}
