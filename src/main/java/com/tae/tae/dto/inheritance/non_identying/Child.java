package com.tae.tae.dto.inheritance.non_identying;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Child {

    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;
    private String name;
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "PARENT_ID1",
//                    referencedColumnName = "PARENT_ID1"),
//            @JoinColumn(name = "PARENT_ID2",
//                    referencedColumnName = "PARENT_ID2")
//    })
//    private Parent parent;]

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
}
