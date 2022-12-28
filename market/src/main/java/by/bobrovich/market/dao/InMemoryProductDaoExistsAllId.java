package by.bobrovich.market.dao;

import by.bobrovich.market.api.ProductDaoExistsAllId;

import java.util.Collection;

public class InMemoryProductDaoExistsAllId extends InMemoryProductDao implements ProductDaoExistsAllId {
    @Override
    public boolean existsAllId(Collection<Integer> ids) {
        if (ids == null || ids.isEmpty()) return false;

        for (Integer id : ids) {
            if (!products.containsKey(id)) {
                return false;
            }
        }

        return true;
    }
}
