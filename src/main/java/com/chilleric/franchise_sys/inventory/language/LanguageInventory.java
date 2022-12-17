package com.chilleric.franchise_sys.inventory.language;

import java.util.Optional;
import com.chilleric.franchise_sys.repository.language.Language;

public interface LanguageInventory {
    Optional<Language> findLanguageById(String id);

    Optional<Language> findLanguageByKey(String key);
}
