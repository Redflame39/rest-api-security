package com.epam.esm.converter;

import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderToOrderDtoConverter implements Converter<Order, OrderDto> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");


    @Override
    public OrderDto convert(Order source) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(source.getId());
        orderDto.setPrice(source.getPrice());
        orderDto.setUserId(source.getUser().getId());
        LocalDateTime createLocalDateTime = source.getCreateDate().toLocalDateTime();
        String createDate = createLocalDateTime.format(FORMATTER);
        orderDto.setCreateDate(createDate);
        List<Certificate> certificates = new ArrayList<>(source.getCertificates());
        orderDto.setCertificates(CertificateDto.toCertificateDtoList(certificates));
        return orderDto;
    }
}
