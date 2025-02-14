package io.seventytwo.vaadinjooq.util;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import org.jooq.Field;
import org.jooq.OrderField;
import org.jooq.Table;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * A utility class that helps to convert between Vaadin and jOOQ
 */
public class VaadinJooqUtil {

    private VaadinJooqUtil() {
    }

    /**
     * Converts {@link QuerySortOrder} to {@link OrderField}
     *
     * @param table The table the OrderFields should use
     * @param query The {@link Query}
     * @return a {@link List} of {@link OrderField}
     */
    public static List<OrderField<?>> orderFields(Table<?> table, Query<?, ?> query) {
        List<OrderField<?>> orderFields = new ArrayList<>();
        for (QuerySortOrder sortOrder : query.getSortOrders()) {
            Field<?> field = table.field(sortOrder.getSorted());
            if (field == null) {
                throw new IllegalArgumentException(format("Field {0} is not a field of {1}", table.getName(), sortOrder.getSorted()));
            }
            orderFields.add(sortOrder.getDirection().equals(SortDirection.DESCENDING) ? field.desc() : field);
        }
        return orderFields;
    }

}
