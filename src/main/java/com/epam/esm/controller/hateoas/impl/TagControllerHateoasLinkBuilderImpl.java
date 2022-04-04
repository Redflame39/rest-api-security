package com.epam.esm.controller.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.controller.hateoas.api.TagControllerHateoasLinkBuilder;
import com.epam.esm.model.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagControllerHateoasLinkBuilderImpl implements TagControllerHateoasLinkBuilder {

    @Override
    public CollectionModel<TagDto> buildReadAllLinks(Collection<TagDto> tagDtos,
                                                     Long tagsCount, Long pageNum, Long pageSize) {
        for (TagDto dto : tagDtos) {
            Link self = linkTo(methodOn(TagController.class)
                    .read(dto.getId()))
                    .withSelfRel();
            dto.add(self);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 0) {
            Link prevPageLink = linkTo(methodOn(TagController.class)
                    .read(pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(TagController.class)
                .read(pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = pageNum * pageSize >= tagsCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(TagController.class)
                    .read(pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(tagDtos, collectionLinks);
    }

    @Override
    public TagDto buildReadSingleLinks(TagDto tagDto) {
        Link self = linkTo(methodOn(TagController.class)
                .read(tagDto.getId()))
                .withSelfRel();
        tagDto.add(self);
        return tagDto;
    }

    @Override
    public TagDto buildCreateLinks(TagDto tagDto) {
        TagDto placeholderTagDto = new TagDto();
        Link self = linkTo(methodOn(TagController.class).create(placeholderTagDto)).withSelfRel();
        Link toCreatedLink = linkTo(methodOn(TagController.class)
                .read(tagDto.getId()))
                .withRel("Read created");
        tagDto.add(self, toCreatedLink);
        return tagDto;
    }

    @Override
    public TagDto buildDeleteLinks(TagDto tagDto) {
        Link self = linkTo(methodOn(TagController.class)
                .delete(tagDto.getId()))
                .withSelfRel();
        tagDto.add(self);
        return tagDto;
    }
}
