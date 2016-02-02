package lu.cortex.model;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Contract defines common properties which are
 *  shared by all data models from domain.
 */
public interface DataModel {
    /**
     * Accessor in reading on the technical identifier.
     * @return The identifier.
     */
    Long getIdentifier();

    /**
     * Accessor in writing on the technical identifier.
     * @param identifier
     */
    void setIdentifier(final Long identifier);

    /**
     * Accessor in reading on the business reference
     *  provided by the domain.
     * @return The reference.
     */
    String getBusinessReference();

    /**
     * Accessor in writing on the business reference
     *  provided by the domain.
     * @param reference The reference.
     */
    void setBusinessReference(final String reference);

    /**
     * Accessor in reading on the current version
     *  associated to the data model instance.
     * @return The version.
     */
    Instant getVersion();

    /**
     * Accessor in writing on the current version
     *  associated to the data model instance.
     * @param version The version.
     */
    void setVersion(final Instant version);

    /**
     * Accessor in reading on the date of creation.
     * @return The creation date.
     */
    LocalDate getCreationDate();

    /**
     * Accessor in writing on the date of creation.
     * @param date The creation date.
     */
    void setCreationDate(final LocalDate date);

    /**
     * Accessor in reading on the date of modification.
     * @return The modification date.
     */
    LocalDate getModificationDate();

    /**
     * Accessor in writing on the date of modification.
     * @param date The modification date.
     */
    void setModificationDate(final LocalDate date);

    /*
    Endpoint getDomain();

    void setDomain(final Endpoint endpoint);
    */
}
