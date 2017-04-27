package com.kritacademy.spring.sample.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Chertpong on 5/10/2016.
 */

@Data
@AllArgsConstructor
public class City {
    String name;
    int population;
}
