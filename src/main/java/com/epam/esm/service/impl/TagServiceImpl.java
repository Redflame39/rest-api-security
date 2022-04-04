package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.api.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final ConversionService conversionService;

    @Override
    public TagDto create(TagDto tag) {
        Tag entity = conversionService.convert(tag, Tag.class);
        Tag createdTag = repository.save(entity);
        return conversionService.convert(createdTag, TagDto.class);
    }

    @Override
    public List<TagDto> findAll(Long pageNum, Long pageSize) {
        Page<Tag> tags = repository.findAll(PageRequest.of(pageNum.intValue(), pageSize.intValue()));
        return TagDto.toTagDtoList(tags.toList());
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
        Optional<Tag> toDelete = repository.findById(deleteId);
        if (!toDelete.isPresent()) {
            throw new EntityNotFoundException("Failed to delete entity with id " + deleteId);
        }
        Tag deleted = toDelete.get();
        repository.delete(deleted);
        return conversionService.convert(deleted, TagDto.class);
    }

    @Override
    public List<TagDto> findMostUsedUserTag(Long userId) {
        List<Tag> tags = repository.findMostWidelyUsedUserTag(userId);
        if (tags.stream().allMatch(Objects::isNull)) {
            return Collections.emptyList();
        }
        return TagDto.toTagDtoList(tags);
    }

    @Override
    public Long countTags() {
        return repository.count();
    }

}
