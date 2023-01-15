package com.chilleric.franchise_sys.inventory.service;

import com.chilleric.franchise_sys.repository.informationRepository.serve.Serve;

import java.util.Optional;

public interface ServeInventory {
    Optional<Serve> findServeById(String id);
}
