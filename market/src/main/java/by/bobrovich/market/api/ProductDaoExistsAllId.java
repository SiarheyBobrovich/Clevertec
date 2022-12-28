package by.bobrovich.market.api;

import java.util.Collection;

public interface ProductDaoExistsAllId extends  ProductDao {
    boolean existsAllId(Collection<Integer> ids);
}
