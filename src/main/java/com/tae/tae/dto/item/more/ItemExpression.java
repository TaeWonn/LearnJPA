package com.tae.tae.dto.item.more;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.StringPath;
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
