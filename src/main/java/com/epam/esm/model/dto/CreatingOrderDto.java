package com.epam.esm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class CreatingOrderDto {

    Long userId;

    List<Long> certificatesIds;

}
