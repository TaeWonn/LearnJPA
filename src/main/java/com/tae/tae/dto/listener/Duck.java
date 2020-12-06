package com.tae.tae.dto.listener;

import com.tae.tae.dto.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@EntityListeners(DuckListener.class)
@ExcludeDefaultListeners
@ExcludeSuperclassListeners
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Duck extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @PrePersist
    public void perPersist() {
        System.out.println("Duck.perPersist id = " + id);
    }

    @PostPersist
    public void postPersist() {
        System.out.println("Duck.postPersist id = " + id);
    }

    @PostLoad
    public void postLoad() {
        System.out.println("DUCK.postLoad");
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Duck.preRemove");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("Duck.postRemove");
    }
}
