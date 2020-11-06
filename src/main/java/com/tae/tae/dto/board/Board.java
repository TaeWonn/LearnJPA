package com.tae.tae.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "BOARD")
@AllArgsConstructor
@NoArgsConstructor
@SecondaryTable(
        name = "BOARD_DETAIL",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID")
)
public class Board {

    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @Column(table = "BOARD_DETAIL")
    private String contents;
}
