package com.tae.tae.service;

import com.tae.tae.dao.BoardRepository;
import com.tae.tae.dto.board.Board;
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
