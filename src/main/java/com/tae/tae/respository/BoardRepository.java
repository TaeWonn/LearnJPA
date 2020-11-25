package com.tae.tae.respository;

import com.tae.tae.dto.board.Board;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {

    private static int dbCount = 0;

    public List<Board> createBySzie(String size) {
        dbCount ++;
        ArrayList<Board> boards = new ArrayList<>();
        int count = Integer.parseInt(size);

        for(int i = 0; i < count; i++ ) {
            boards.add(new Board(i + 0L, i + "번째 게시물", i + "번째 내용"));
        }

        return  boards;
    }

    public static int getDbCount() {
        return dbCount;
    }
}
