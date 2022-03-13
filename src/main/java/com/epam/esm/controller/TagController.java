package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.api.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    private final TagService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> read(@RequestParam(name = "page", required = false, defaultValue = "1")
                                        @Min(value = 1, message = "Page should be represented as positive number")
                                                Integer pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                        @Min(value = 1, message = "Page size should be represented as positive number")
                                        @Max(value = 100, message = "Page size should not be greater than 100")
                                                Integer pageSize) {
        List<TagDto> tagDtos = service.findAll(pageNum, pageSize);
        Long tagsCount = service.countTags();
        for (TagDto dto : tagDtos) {
            Link self = linkTo(methodOn(TagController.class)
                    .read(dto.getId()))
                    .withSelfRel();
            dto.add(self);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 1) {
            Link prevPageLink = linkTo(methodOn(TagController.class)
                    .read(pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(TagController.class)
                .read(pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = (long) pageNum * pageSize >= tagsCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(TagController.class)
                    .read(pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(tagDtos, collectionLinks);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto read(@PathVariable Long id) {
        TagDto dto = service.findById(id);
        Link self = linkTo(methodOn(TagController.class)
                .read(id))
                .withSelfRel();
        dto.add(self);
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tag) {
        TagDto dto = service.create(tag);
        Link self = linkTo(methodOn(TagController.class).create(tag)).withSelfRel();
        Link toCreatedLink = linkTo(methodOn(TagController.class)
                .read(dto.getId()))
                .withRel("Read created");
        dto.add(self, toCreatedLink);
        return dto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto delete(@PathVariable Long id) {
        TagDto dto = service.delete(id);
        Link self = linkTo(methodOn(TagController.class)
                .delete(id))
                .withSelfRel();
        dto.add(self);
        return dto;
    }
}