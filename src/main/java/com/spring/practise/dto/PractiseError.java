package com.spring.practise.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PractiseError extends BaseOutputDto implements Serializable {
    private String description;
    private String additionalInfo;
}
