package com.epam.esm.service.impl;

import com.epam.esm.repository.NativeSpecification;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.specification.MostWidelyUsedUserTagSpecification;
import com.epam.esm.service.api.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagServiceImpl implements TagService {

    private final TagRepository<Long> repository;
    private final ConversionService conversionService;

    @Override
    public TagDto create(TagDto tag) {
        Tag createdTag = repository.create(tag);
        return conversionService.convert(createdTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAll(Integer pageNum, Integer pageSize) {
        List<Tag> tags = repository.findAll(pageNum, pageSize);
        return TagDto.toTagDtoList(tags);
    }

    @Override
    public List<TagDto> findByCertificateId(Long id) {
        List<Tag> tags = repository.findByCertificateId(id);
        return TagDto.toTagDtoList(tags);
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> tag = repository.findById(id);
        Tag item = tag.orElseThrow(
                () -> new EntityNotFoundException("Tag with id " + id + " cannot be found"));
        return conversionService.convert(item, TagDto.class);
    }

    @Override
    public TagDto delete(Long deleteId) {
        Tag deletedTag = repository.delete(deleteId);
        return conversionService.convert(deletedTag, TagDto.class);
    }

    @Override
    public List<TagDto> findMostUsedUserTag(Long userId) {
        NativeSpecification specification = new MostWidelyUsedUserTagSpecification(userId);
        List<Tag> tags = repository.findByNativeSpecification(specification);
        if (tags.stream().allMatch(Objects::isNull)) {
            return Collections.emptyList();
        }
        return TagDto.toTagDtoList(tags);
    }

    @Override
    public Long countTags() {
        return repository.countTags();
    }
}
