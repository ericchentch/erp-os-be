package com.chilleric.franchise_sys.inventory.service;

import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.informationRepository.serve.Serve;
import com.chilleric.franchise_sys.repository.informationRepository.serve.ServeRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

@Service
public class ServeInventoryImpl extends AbstractInventory<ServeRepository> implements ServeInventory {
    @Override
    public Optional<Serve> findServeById(String id) {
        List<Serve> Serves = repository.getServes(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
        if (Serves.size() != 0) {
            return Optional.of(Serves.get(0));
        }
        return Optional.empty();
    }

}
