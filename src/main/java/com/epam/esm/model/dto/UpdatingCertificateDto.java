package com.epam.esm.model.dto;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatingCertificateDto {

    Double price;

    Integer duration;

    String name;

    String description;

    Set<TagDto> tags;

}
