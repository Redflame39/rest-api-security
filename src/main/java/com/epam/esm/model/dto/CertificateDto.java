package com.epam.esm.model.dto;

import com.epam.esm.converter.CertificateToCertificateDtoConverter;
import com.epam.esm.model.entity.Certificate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class CertificateDto extends RepresentationModel<CertificateDto> {

    Long id;

    Double price;

    Integer duration;

    String name;

    String description;

    String createDate;

    String lastUpdateDate;

    List<TagDto> tags;

    public static List<CertificateDto> toCertificateDtoList(List<Certificate> certificates) {
        CertificateToCertificateDtoConverter converter = new CertificateToCertificateDtoConverter();
        return certificates.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
