package lu.cortex.domain.policy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.Cover;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name="COVER_DEFINITION")
public class CoverSettingsDefault extends AbstractDataModel {

    Cover cover;

    public CoverSettingsDefault(){

    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
