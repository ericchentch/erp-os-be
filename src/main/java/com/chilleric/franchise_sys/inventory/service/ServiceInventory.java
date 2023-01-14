package com.chilleric.franchise_sys.inventory.service;

import com.chilleric.franchise_sys.repository.informationRepository.service.Service;

import java.util.Optional;

public interface ServiceInventory {
    Optional<Service> findServiceById(String id);
}
