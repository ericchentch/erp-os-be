package com.chilleric.franchise_sys.repository.systemRepository.language;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class LanguageRepositoryImpl extends AbstractRepo implements LanguageRepository {

    @Override
    public Optional<List<Language>> getLanguages(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Language.class, keySort, sortField, page, pageSize);
        return systemFind(query, Language.class);
    }

    @Override
    public void insertAndUpdate(Language language) {
        systemDBTemplate.save(language, "languages");
    }

    @Override
    public long getTotal(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Language.class, "", "", 0, 0);
        long total = systemDBTemplate.count(query, Language.class);
        return total;
    }

}
