package com.spring.practise.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeatureFlag {

    private List<String> sites;
    private List<String> users;
}
