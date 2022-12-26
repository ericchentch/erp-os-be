package com.chilleric.franchise_sys.repository.informationRepository.shift;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ShiftRepositoryImpl extends AbstractRepo implements ShiftRepository {
    @Override
    public Optional<List<Shift>> getShifts(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Shift.class, keySort, sortField, page, pageSize);
        return informationFind(query, Shift.class);
    }

    @Override
    public void insertAndUpdate(Shift shift) {
        informationDBTemplate.save(shift, "shifts");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Shift.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Shift.class);
    }

}
