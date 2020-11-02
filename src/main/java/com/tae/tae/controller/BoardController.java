package com.tae.tae.controller;

import com.tae.tae.service.BoardService;
import com.tae.tae.dto.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping()
    public List<Board> boards(String size) {
        List<Board> boards = boardService.getBoards(size);
        return boards;
    }

    @GetMapping("count")
    public int count() {
        return BoardService.getDbCount();
    }
}
