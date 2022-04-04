package com.epam.esm.model.audit;

import java.sql.Timestamp;

/**
 * Interface should be implemented by all entities that needs to be audited.
 */
public interface AuditableEntity {

    /**
     * Returns last update date of entity.
     *
     * @return timestamp of last update date
     */
    Timestamp getLastUpdateDate();

    /**
     * Sets last update date of entity.
     *
     * @param t timestamp of updating
     */
    void setLastUpdateDate(Timestamp t);

    /**
     * Returns create date of entity.
     *
     * @return timestamp of create date
     */
    Timestamp getCreateDate();

    /**
     * Sets create date of entity.
     *
     * @param t timestamp of creation
     */
    void setCreateDate(Timestamp t);

}
