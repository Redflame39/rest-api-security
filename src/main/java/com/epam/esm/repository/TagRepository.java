package com.epam.esm.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "select t.id, t.name " +
            "from users u " +
            "         left outer join orders o on u.id = o.user_id " +
            "         left outer join certificates_orders co on o.id = co.order_id " +
            "         left outer join gift_certificate gc on co.certificate_id = gc.id " +
            "         left outer join certificate_tags ct on gc.id = ct.certificate_id " +
            "         left outer join tag t on ct.tag_id = t.id " +
            "where u.id = :userId " +
            "group by t.id, t.name " +
            "order by count(t.id) desc limit 1;",
            nativeQuery = true)
    List<Tag> findMostWidelyUsedUserTag(@Param("userId") Long userId);

    Optional<Tag> findByName(String name);

    @Query("select t from Tag t left join Certificate c on c.id = :certificateId")
    List<Tag> findByCertificateId(@Param("certificateId") Long certificateId);
}
