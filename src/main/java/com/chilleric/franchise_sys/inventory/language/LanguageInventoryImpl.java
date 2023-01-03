package com.chilleric.franchise_sys.inventory.language;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.systemRepository.language.Language;
import com.chilleric.franchise_sys.repository.systemRepository.language.LanguageRepository;

@Service
public class LanguageInventoryImpl extends AbstractInventory<LanguageRepository>
        implements LanguageInventory {
    @Override
    public Optional<Language> findLanguageById(String id) {
        List<Language> languages =
                repository.getLanguages(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
        if (languages.size() == 0)
            return Optional.empty();
        return Optional.of(languages.get(0));
    }

    @Override
    public Optional<Language> findLanguageByKey(String key) {
        List<Language> languages =
                repository.getLanguages(Map.ofEntries(entry("key", key)), "", 0, 0, "").get();
        if (languages.size() == 0)
            return Optional.empty();
        return Optional.of(languages.get(0));
    }
}
