package com.tae.tae.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "board")
    @OrderColumn(name = "POSITION")
    private List<Comment> commentList = new ArrayList<>();

    public Board(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
