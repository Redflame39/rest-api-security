package com.epam.esm.controller.hateoas.api;

import com.epam.esm.model.dto.CertificateDto;
import org.springframework.hateoas.CollectionModel;

import java.util.Collection;

public interface CertificateControllerHateoasLinkBuilder {

    CollectionModel<CertificateDto> buildReadAllLinks(Collection<CertificateDto> certificateDtos, Long certificatesCount,
                                                      Long pageNum, Long pageSize);

    CertificateDto buildReadSingleLinks(CertificateDto certificateDto);

    CertificateDto buildCreateLinks(CertificateDto certificateDto);

    CertificateDto buildUpdateLinks(CertificateDto certificateDto);

    CertificateDto buildUpdatePriceLinks(CertificateDto certificateDto);

    CertificateDto buildDeleteLinks(CertificateDto certificateDto);

}
