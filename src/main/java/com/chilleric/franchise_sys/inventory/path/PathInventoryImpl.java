package com.chilleric.franchise_sys.inventory.path;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;
import com.chilleric.franchise_sys.repository.systemRepository.path.PathRepository;

@Service
public class PathInventoryImpl extends AbstractInventory<PathRepository> implements PathInventory {

    @Override
    public Optional<Path> findPathByLabel(String label) {
        List<Path> paths =
                repository.getPaths(Map.ofEntries(entry("label", label)), "", 0, 0, "").get();
        if (paths.size() == 0)
            return Optional.empty();
        return Optional.of(paths.get(0));
    }

    @Override
    public Optional<Path> findPathByPath(String path) {
        List<Path> paths =
                repository.getPaths(Map.ofEntries(entry("path", path)), "", 0, 0, "").get();
        if (paths.size() == 0)
            return Optional.empty();
        return Optional.of(paths.get(0));
    }

    @Override
    public Optional<Path> findPathById(String id) {
        List<Path> paths = repository.getPaths(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
        if (paths.size() == 0)
            return Optional.empty();
        return Optional.of(paths.get(0));
    }

}
