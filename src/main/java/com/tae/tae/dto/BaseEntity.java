package com.tae.tae.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    private Date createDate;    //등록일
    private Date updateDate;    //수정일
}
