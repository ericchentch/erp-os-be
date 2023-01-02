package com.chilleric.franchise_sys.inventory.shift;

import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.informationRepository.shift.Shift;
import com.chilleric.franchise_sys.repository.informationRepository.shift.ShiftRepository;
import com.chilleric.franchise_sys.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

@Service
public class ShiftInventoryImpl extends AbstractInventory<ShiftRepository> implements ShiftInventory{

    @Override
    public Optional<Shift> findShiftById(String id) {
        List<Shift> shifts = repository.getShifts(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
        if (shifts.size() != 0) {
            return Optional.of(shifts.get(0));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Shift> findShiftByName(String name) {
        String normalizeName = StringUtils.normalizeString(name);
        List<Shift> shifts = repository.getShifts(Map.ofEntries(entry("shiftName", normalizeName)), "", 0, 0, "").get();
        if (shifts.size() != 0) {
            return Optional.of(shifts.get(0));
        }
        return Optional.empty();
    }
}
