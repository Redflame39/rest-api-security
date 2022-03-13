package com.epam.esm.repository.specification;

import com.epam.esm.repository.NativeSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MostWidelyUsedUserTagSpecification implements NativeSpecification {

    private static final String QUERY = "select t.id, t.name " +
            "from users u " +
            "         left outer join orders o on u.id = o.user_id " +
            "         left outer join certificates_orders co on o.id = co.order_id " +
            "         left outer join gift_certificate gc on co.certificate_id = gc.id " +
            "         left outer join certificate_tags ct on gc.id = ct.certificate_id " +
            "         left outer join tag t on ct.tag_id = t.id " +
            "where u.id = ? " +
            "group by t.id, t.name " +
            "order by count(t.id) desc limit 1;";

    private final Long userId;

    @Override
    public String getNativeQuery() {
        return QUERY.replace("?", Long.toString(userId));
    }
}
