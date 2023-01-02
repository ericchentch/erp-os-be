package com.chilleric.franchise_sys.inventory.shift;

import com.chilleric.franchise_sys.repository.informationRepository.shift.Shift;

import java.util.Optional;

public interface ShiftInventory {
    Optional<Shift> findShiftById(String id);

    Optional<Shift> findShiftByName(String name);
}
