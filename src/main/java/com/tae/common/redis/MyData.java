package com.tae.common.redis;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyData implements Serializable {
    private static final long serialVersionUId = -7353484588260422449L;
    private String id;
    private String name;

}
