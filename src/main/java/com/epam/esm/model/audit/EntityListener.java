package com.epam.esm.model.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class EntityListener {

    @PrePersist
    private void onPreCreate(AuditableEntity entity) {
        Timestamp currentTime = Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime());
        entity.setCreateDate(currentTime);
        entity.setLastUpdateDate(currentTime);
    }

    @PreUpdate
    private void onPreUpdate(AuditableEntity entity) {
        Timestamp currentTime = Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime());
        entity.setLastUpdateDate(currentTime);
    }
}
