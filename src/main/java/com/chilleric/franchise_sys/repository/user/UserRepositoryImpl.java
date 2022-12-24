package com.chilleric.franchise_sys.repository.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractSystemRepo;

@Repository
public class UserRepositoryImpl extends AbstractSystemRepo implements UserRepository {

    @Override
    public Optional<List<User>> getUsers(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, User.class, keySort, sortField, page, pageSize);
        return replaceFind(query, User.class);
    }

    @Override
    public void insertAndUpdate(User user) {
        systemDBTemplate.save(user, "users");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, User.class, "", "", 0, 0);
        long total = systemDBTemplate.count(query, User.class);
        return total;
    }
}
