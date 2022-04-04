package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.api.TagControllerHateoasLinkBuilder;
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
    private final TagControllerHateoasLinkBuilder linkBuilder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> read(@RequestParam(name = "page", required = false, defaultValue = "0")
                                        @Min(value = 0, message = "Page size should be greater than or equal to zero")
                                                Long pageNum,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                        @Min(value = 1, message = "Page size should be represented as positive number")
                                        @Max(value = 100, message = "Page size should not be greater than 100")
                                                Long pageSize) {
        Long tagsCount = service.countTags();
        List<TagDto> tagDtos = service.findAll(pageNum, pageSize);
        return linkBuilder.buildReadAllLinks(tagDtos, tagsCount, pageNum, pageSize);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto read(@PathVariable Long id) {
        TagDto dto = service.findById(id);
        return linkBuilder.buildReadSingleLinks(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tag) {
        TagDto dto = service.create(tag);
        return linkBuilder.buildCreateLinks(dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto delete(@PathVariable Long id) {
        TagDto dto = service.delete(id);
        return linkBuilder.buildDeleteLinks(dto);
    }
}