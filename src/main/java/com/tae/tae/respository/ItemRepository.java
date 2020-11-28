package com.tae.tae.respository;

import com.tae.tae.dto.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>/*,
                                        QuerydslPredicateExecutor<Item> */{


}
