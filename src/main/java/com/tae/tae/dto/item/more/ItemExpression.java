package com.tae.tae.dto.item.more;


import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.tae.tae.dto.item.Item;
import com.tae.tae.dto.item.QItem;

public class ItemExpression {

    @QueryDelegate(Item.class)
    public static BooleanExpression isExpensive(QItem item, Integer price) {
        return item.price.gt(price);
    }

    @QueryDelegate(String.class)
    public static BooleanExpression isHelloStart(StringPath stringPath) {
        return  stringPath.startsWith("hello");
    }
}
