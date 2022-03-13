package com.epam.esm.repository.specification;

import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CriteriaSpecification;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SpecificationCreator {

    public CriteriaSpecification<Certificate> createCertificateSpecification(CertificateQueryDto certificatesQueryDto) {
        if (certificatesQueryDto == null) {
            return new CertificatesQuerySpecification(Collections.emptyList());
        }
        List<CriteriaSpecification<Certificate>> certificatesSpecifications = new ArrayList<>();
        List<TagDto> tags = certificatesQueryDto.getTags();
        if (certificatesQueryDto.getTags() != null && !certificatesQueryDto.getTags().isEmpty()) {
            var certificatesByTagsSpecification = new CertificatesBySeveralTagsSpecification(TagDto.toTagList(tags));
            certificatesSpecifications.add(certificatesByTagsSpecification);
        }
        String namePart = certificatesQueryDto.getName();
        if (namePart != null && !namePart.trim().isEmpty()) {
            var certificatesByNamePartSpecification = new CertificatesByNamePartSpecification(namePart);
            certificatesSpecifications.add(certificatesByNamePartSpecification);
        }
        String descriptionPart = certificatesQueryDto.getDescription();
        if (descriptionPart != null && !descriptionPart.trim().isEmpty()) {
            var certificatesByDescriptionPartSpecification = new CertificatesByDescriptionPartSpecification(descriptionPart);
            certificatesSpecifications.add(certificatesByDescriptionPartSpecification);
        }
        return new CertificatesQuerySpecification(certificatesSpecifications);
    }

}
