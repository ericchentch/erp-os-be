package com.chilleric.franchise_sys.inventory.service;

import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.informationRepository.service.Service;
import com.chilleric.franchise_sys.repository.informationRepository.service.ServiceRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

@org.springframework.stereotype.Service
public class ServiceInventoryImpl extends AbstractInventory<ServiceRepository> implements ServiceInventory {
    @Override
    public Optional<Service> findServiceById(String id) {
        List<Service> services = repository.getServices(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
        if (services.size() != 0) {
            return Optional.of(services.get(0));
        }
        return Optional.empty();
    }

}
