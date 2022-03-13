package com.epam.esm.model.entity;

import com.epam.esm.model.audit.AuditableEntity;
import com.epam.esm.model.audit.EntityListener;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@EntityListeners(EntityListener.class)
@Entity
@Table(name = "orders")
public class Order implements AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "create_date")
    private Timestamp createDate;

    // Attribute is marked as Transient, because it is not present on data model, but required in jpa entity definition
    // for correct implementing AuditableEntity interface
    @Transient
    private Timestamp lastUpdateDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "certificates_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    private Set<Certificate> certificates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (createDate != null ? !createDate.equals(order.createDate) : order.createDate != null) return false;
        if (certificates != null ? !certificates.equals(order.certificates) : order.certificates != null) return false;
        return user != null ? user.equals(order.user) : order.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (certificates != null ? certificates.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", price=").append(price);
        sb.append(", createDate=").append(createDate);
        sb.append(", certificates=").append(certificates);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }



}
