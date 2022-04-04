package com.epam.esm.converter;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToTagDtoConverter implements Converter<Tag, TagDto> {

    @Override
    public TagDto convert(Tag source) {
        return TagDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }

}
