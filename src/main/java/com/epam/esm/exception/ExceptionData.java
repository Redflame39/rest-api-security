package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class ExceptionData {

    String name;

    String titleMessage;

    String exceptionMessage;

    Integer code;

}
