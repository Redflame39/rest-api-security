package com.epam.esm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CertificateQueryDto {

    List<TagDto> tags = new ArrayList<>();

    String name;

    String description;

}