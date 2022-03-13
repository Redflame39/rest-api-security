package com.epam.esm.converter;

import com.epam.esm.model.dto.UpdatingCertificateDto;
import com.epam.esm.model.entity.Certificate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdatingCertificateDtoToCertificateConverter implements Converter<UpdatingCertificateDto, Certificate> {

    @Override
    public Certificate convert(UpdatingCertificateDto source) {
        Certificate certificate = new Certificate();
        certificate.setName(source.getName());
        certificate.setDescription(source.getDescription());
        certificate.setPrice(source.getPrice());
        certificate.setDuration(source.getDuration());
        return certificate;
    }
}
