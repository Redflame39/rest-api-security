package com.epam.esm.model.dto;

import com.epam.esm.converter.TagDtoToTagConverter;
import com.epam.esm.converter.TagToTagDtoConverter;
import com.epam.esm.model.entity.Tag;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class TagDto extends RepresentationModel<TagDto> {

    Long id;

    String name;

    public static List<Tag> toTagList(Collection<TagDto> tagDtos) {
        TagDtoToTagConverter converter = new TagDtoToTagConverter();
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            Tag tag = converter.convert(tagDto);
            tags.add(tag);
        }
        return tags;
    }

    public static List<TagDto> toTagDtoList(Collection<Tag> tags) {
        TagToTagDtoConverter converter = new TagToTagDtoConverter();
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagDto = converter.convert(tag);
            tagDtos.add(tagDto);
        }
        return tagDtos;
    }

}
