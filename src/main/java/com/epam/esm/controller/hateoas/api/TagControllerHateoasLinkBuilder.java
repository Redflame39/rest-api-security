package com.epam.esm.controller.hateoas.api;

import com.epam.esm.model.dto.TagDto;
import org.springframework.hateoas.CollectionModel;

import java.util.Collection;

public interface TagControllerHateoasLinkBuilder {

    CollectionModel<TagDto> buildReadAllLinks(Collection<TagDto> tagDtos, Long tagsCount, Long pageNum, Long pageSize);

    TagDto buildReadSingleLinks(TagDto tagDto);

    TagDto buildCreateLinks(TagDto tagDto);

    TagDto buildDeleteLinks(TagDto tagDto);

}
