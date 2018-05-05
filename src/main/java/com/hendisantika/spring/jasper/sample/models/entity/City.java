package com.hendisantika.spring.jasper.sample.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : backend
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 05/05/18
 * Time: 08.54
 * To change this template use File | Settings | File Templates.
 */

@Data
@AllArgsConstructor
public class City {
    String name;
    int population;
}
