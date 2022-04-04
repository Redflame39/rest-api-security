package com.epam.esm.service.impl;

import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.UpdatingCertificateDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.service.api.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final ConversionService conversionService;
    private final SpecificationCreator specificationCreator;

    @Override
    public List<CertificateDto> findAll(CertificateQueryDto certificatesQueryDto, Long pageNum, Long pageSize) {
        if (certificatesQueryDto != null) {
            Set<Tag> tags = loadTagsByName(certificatesQueryDto.getTags());
            List<Tag> tagList = new ArrayList<>(tags);
            certificatesQueryDto.setTags(TagDto.toTagDtoList(tagList));
        }
        var specification = specificationCreator.createCertificateSpecification(certificatesQueryDto);
        Page<Certificate> certificates = certificateRepository.findAll(specification,
                PageRequest.of(pageNum.intValue(), pageSize.intValue()));
        return CertificateDto.toCertificateDtoList(certificates.getContent());
    }

    @Override
    @Transactional
    public CertificateDto create(UpdatingCertificateDto certificate) {
        Certificate entity = conversionService.convert(certificate, Certificate.class);
        Set<Tag> tags = loadTagsByName(certificate.getTags());
        entity.setTags(tags);
        Certificate createdCertificate = certificateRepository.save(entity);
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
        entity.setId(updateId);
        Set<Tag> tags = loadTagsByName(replacement.getTags());
        entity.setTags(tags);
        Certificate updatedCertificate = certificateRepository.save(entity);
        return conversionService.convert(updatedCertificate, CertificateDto.class);
    }

    @Override
    @Transactional
    public CertificateDto updatePrice(Long updateId, Double newPrice) {
        Certificate certificate = new Certificate();
        certificate.setPrice(newPrice);
        certificate.setId(updateId);
        Certificate updatedCertificate = certificateRepository.save(certificate);
        return conversionService.convert(updatedCertificate, CertificateDto.class);
    }

    @Override
    public CertificateDto delete(Long deleteId) {
        Optional<Certificate> toDelete = certificateRepository.findById(deleteId);
        if (!toDelete.isPresent()) {
            throw new EntityNotFoundException("Certificate to delete not found, id: " + deleteId);
        }
        certificateRepository.deleteById(deleteId);
        return conversionService.convert(toDelete.get(), CertificateDto.class);
    }

    private Set<Tag> loadTagsByName(Collection<TagDto> tags) {
        Set<Tag> updatedTags = new HashSet<>();
        for (TagDto t : tags) {
            Optional<Tag> tagFound = tagRepository.findByName(t.getName());
            if (tagFound.isPresent()) {
                updatedTags.add(tagFound.get());
            } else {
                Tag toSave = conversionService.convert(t, Tag.class);
                Tag tagCreated = tagRepository.save(toSave);
                updatedTags.add(tagCreated);
            }
        }
        return updatedTags;
    }

    @Override
    public Long countCertificates() {
        return certificateRepository.count();
    }
}
