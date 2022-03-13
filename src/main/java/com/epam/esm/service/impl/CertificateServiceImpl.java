package com.epam.esm.service.impl;

import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.api.CertificateRepository;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.UpdatingCertificateDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.service.api.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository<Long> certificateRepository;
    private final TagRepository<Long> tagRepository;
    private final ConversionService conversionService;
    private final SpecificationCreator specificationCreator;

    @Override
    public List<CertificateDto> findAll(CertificateQueryDto certificatesQueryDto, Integer pageNum, Integer pageSize) {
        if (certificatesQueryDto != null) {
            Set<Tag> tags = loadTagsByName(certificatesQueryDto.getTags());
            List<Tag> tagList = new ArrayList<>(tags);
            certificatesQueryDto.setTags(TagDto.toTagDtoList(tagList));
        }
        var specification = specificationCreator.createCertificateSpecification(certificatesQueryDto);
        List<Certificate> certificates = certificateRepository.findAll(specification, pageNum, pageSize);
        return CertificateDto.toCertificateDtoList(certificates);
    }

    @Override
    @Transactional
    public CertificateDto create(UpdatingCertificateDto certificate) {
        Certificate entity = conversionService.convert(certificate, Certificate.class);
        Set<Tag> tags = loadTagsByName(certificate.getTags());
        entity.setTags(tags);
        Certificate createdCertificate = certificateRepository.create(entity);
        return conversionService.convert(createdCertificate, CertificateDto.class);
    }

    @Override
    public CertificateDto findById(Long id) {
        Optional<Certificate> certificate = certificateRepository.findById(id);
        Certificate item = certificate.orElseThrow(
                () -> new EntityNotFoundException("Cannot find certificate with id " + id));
        return conversionService.convert(item, CertificateDto.class);
    }

    @Override
    @Transactional
    public CertificateDto update(Long updateId, UpdatingCertificateDto replacement) {
        Certificate entity = conversionService.convert(replacement, Certificate.class);
        Set<Tag> tags = loadTagsByName(replacement.getTags());
        entity.setTags(tags);
        Certificate updatedCertificate = certificateRepository.update(updateId, entity);
        return conversionService.convert(updatedCertificate, CertificateDto.class);
    }

    @Override
    @Transactional
    public CertificateDto updatePrice(Long updateId, Double newPrice) {
        Certificate updatedCertificate = certificateRepository.updatePrice(updateId, newPrice);
        return conversionService.convert(updatedCertificate, CertificateDto.class);
    }

    @Override
    public CertificateDto delete(Long deleteId) {
        Certificate deletedCertificate = certificateRepository.delete(deleteId);
        return conversionService.convert(deletedCertificate, CertificateDto.class);
    }

    private Set<Tag> loadTagsByName(Collection<TagDto> tags) {
        Set<Tag> updatedTags = new HashSet<>();
        for (TagDto t : tags) {
            Optional<Tag> tagFound = tagRepository.findByName(t.getName());
            if (tagFound.isPresent()) {
                updatedTags.add(tagFound.get());
            } else {
                Tag tagCreated = tagRepository.create(t);
                updatedTags.add(tagCreated);
            }
        }
        return updatedTags;
    }

    @Override
    public Long countCertificates() {
        return certificateRepository.countCertificates();
    }
}
