package com.chilleric.franchise_sys.service.shift;

import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.shift.ShiftRequest;
import com.chilleric.franchise_sys.dto.shift.ShiftResponse;

import java.util.Map;
import java.util.Optional;

public interface ShiftService {
    void createShift(ShiftRequest shiftRequest);

    Optional<ListWrapperResponse<ShiftResponse>> getShifts (Map<String, String> allParams, String keySort,
                                                           int page, int pageSize, String sortField);
    Optional<ShiftResponse> getShiftById (String shiftId);
    Optional<ShiftResponse> searchShiftByName (String shiftName);
    void updateShift(ShiftRequest shiftRequest, String shiftId);

    void deleteShift(String shiftId);
}
