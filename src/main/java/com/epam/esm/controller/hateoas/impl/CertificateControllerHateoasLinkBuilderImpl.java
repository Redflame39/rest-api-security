package com.epam.esm.controller.hateoas.impl;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.hateoas.api.CertificateControllerHateoasLinkBuilder;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.UpdatingCertificateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateControllerHateoasLinkBuilderImpl implements CertificateControllerHateoasLinkBuilder {

    @Override
    public CollectionModel<CertificateDto> buildReadAllLinks(Collection<CertificateDto> certificateDtos,
                                                             Long certificatesCount, Long pageNum, Long pageSize) {
        CertificateQueryDto placeholderQueryDto = new CertificateQueryDto();
        for (CertificateDto dto : certificateDtos) {
            Link certificateLink = linkTo(methodOn(CertificateController.class).read(dto.getId())).withSelfRel();
            dto.add(certificateLink);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 0) {
            Link prevPageLink = linkTo(methodOn(CertificateController.class)
                    .read(placeholderQueryDto, pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(CertificateController.class)
                .read(placeholderQueryDto, pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = pageNum * pageSize >= certificatesCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(CertificateController.class)
                    .read(placeholderQueryDto, pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(certificateDtos, collectionLinks);
    }

    @Override
    public CertificateDto buildReadSingleLinks(CertificateDto certificateDto) {
        Link self = linkTo(methodOn(CertificateController.class).read(certificateDto.getId())).withSelfRel();
        certificateDto.add(self);
        return certificateDto;
    }

    @Override
    public CertificateDto buildCreateLinks(CertificateDto certificateDto) {
        UpdatingCertificateDto placeholderCertificateDto = new UpdatingCertificateDto();
        Link self = linkTo(methodOn(CertificateController.class).create(placeholderCertificateDto)).withSelfRel();
        Link toCreatedLink = linkTo(methodOn(CertificateController.class)
                .read(certificateDto.getId()))
                .withRel("Read created");
        certificateDto.add(self, toCreatedLink);
        return certificateDto;
    }

    @Override
    public CertificateDto buildUpdateLinks(CertificateDto certificateDto) {
        UpdatingCertificateDto placeholderCertificateDto = new UpdatingCertificateDto();
        Link self = linkTo(methodOn(CertificateController.class)
                .update(certificateDto.getId(), placeholderCertificateDto))
                .withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(CertificateController.class)
                .read(certificateDto.getId()))
                .withRel("Read updated");
        certificateDto.add(self, toUpdatedLink);
        return certificateDto;
    }

    @Override
    public CertificateDto buildUpdatePriceLinks(CertificateDto certificateDto) {
        Link self = linkTo(methodOn(CertificateController.class).updatePrice(certificateDto.getId(),
                certificateDto.getPrice()))
                .withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(CertificateController.class)
                .read(certificateDto.getId()))
                .withRel("Read updated");
        certificateDto.add(self, toUpdatedLink);
        return certificateDto;
    }

    @Override
    public CertificateDto buildDeleteLinks(CertificateDto certificateDto) {
        Link self = linkTo(methodOn(CertificateController.class)
                .delete(certificateDto.getId()))
                .withSelfRel();
        certificateDto.add(self);
        return certificateDto;
    }
}
