package com.epam.esm.model.dto;

import com.epam.esm.converter.OrderToOrderDtoConverter;
import com.epam.esm.model.entity.Order;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {

    Long id;

    Long userId;

    Double price;

    String createDate;

    List<CertificateDto> certificates;

    public static List<OrderDto> toOrderDtoList(List<Order> orders) {
        OrderToOrderDtoConverter converter = new OrderToOrderDtoConverter();
        return orders.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

}
