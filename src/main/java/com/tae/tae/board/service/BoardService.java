package com.tae.tae.board.service;

import com.tae.tae.board.BoardRepository;
import com.tae.tae.board.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Cacheable(key = "#size", value = "getBoards")
    public List<Board> getBoards(String size) {
        return repository.createBySzie(size);
    }

    public static int getDbCount() {
        return BoardRepository.getDbCount();
    }
}
