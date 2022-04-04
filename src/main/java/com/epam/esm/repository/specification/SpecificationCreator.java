package com.epam.esm.repository.specification;

import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Certificate;
import lombok.var;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecificationCreator {

    public Specification<Certificate> createCertificateSpecification(CertificateQueryDto certificatesQueryDto) {
        Specification<Certificate> resultSpecification = Specification.where(null);
        if (certificatesQueryDto == null) {
            return resultSpecification;
        }
        List<TagDto> tags = certificatesQueryDto.getTags();
        if (certificatesQueryDto.getTags() != null && !certificatesQueryDto.getTags().isEmpty()) {
            var certificatesByTagsSpecification = new CertificatesBySeveralTagsSpecification(TagDto.toTagList(tags));
            resultSpecification.and(certificatesByTagsSpecification);
        }
        String namePart = certificatesQueryDto.getName();
        if (namePart != null && !namePart.trim().isEmpty()) {
            var certificatesByNamePartSpecification = new CertificatesByNamePartSpecification(namePart);
            resultSpecification.and(certificatesByNamePartSpecification);
        }
        String descriptionPart = certificatesQueryDto.getDescription();
        if (descriptionPart != null && !descriptionPart.trim().isEmpty()) {
            var certificatesByDescriptionPartSpecification = new CertificatesByDescriptionPartSpecification(descriptionPart);
            resultSpecification.and(certificatesByDescriptionPartSpecification);
        }
        return resultSpecification;
    }

}
