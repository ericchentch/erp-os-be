package com.chilleric.franchise_sys.inventory.shift;

import java.util.Optional;
import com.chilleric.franchise_sys.repository.crm_repository.shift.Shift;

public interface ShiftInventory {
  Optional<Shift> findShiftById(String id);

  Optional<Shift> findShiftByName(String name);
}
