package com.chilleric.franchise_sys.service.shift;

import com.chilleric.franchise_sys.constant.DateTime;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.shift.ShiftRequest;
import com.chilleric.franchise_sys.dto.shift.ShiftResponse;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.shift.ShiftInventory;
import com.chilleric.franchise_sys.repository.informationRepository.shift.Shift;
import com.chilleric.franchise_sys.repository.informationRepository.shift.ShiftRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.utils.DateFormat;
import com.chilleric.franchise_sys.utils.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftServiceImpl extends AbstractService<ShiftRepository>
        implements ShiftService{

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ShiftInventory shiftInventory;

    @Override
    public void createShift(ShiftRequest shiftRequest) {
        validate(shiftRequest);
        Map<String, String> error = generateError(ShiftRequest.class);
        shiftInventory.findShiftByName(shiftRequest.getShiftName().toLowerCase())
                .ifPresent(shift -> {
                    error.put("key", LanguageMessageKey.SHIFT_NAME_EXISTED);
                    throw new InvalidRequestException(error,
                            LanguageMessageKey.SHIFT_NAME_EXISTED);
                });

        Shift shift = new Shift();
        ObjectId newId = new ObjectId();
        shift.set_id(newId);
        String normalizeName = StringUtils.normalizeString(shiftRequest.getShiftName());
        shift.setShiftName(normalizeName);
        String startCombine = DateFormat.combineHourAndDate(shiftRequest.getStartDate(), shiftRequest.getStartHour());
        String endCombine = DateFormat.combineHourAndDate(shiftRequest.getEndDate(), shiftRequest.getEndHour());
        DateFormat.convertStringToDate(startCombine, DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN);
        DateFormat.convertStringToDate(endCombine, DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN);
        shiftRepository.insertAndUpdate(shift);
    }

    @Override
    public Optional<ListWrapperResponse<ShiftResponse>> getShifts(Map<String, String> allParams, String keySort, int page, int pageSize, String sortField) {
        List<Shift> shifts = repository.getShifts(allParams, keySort, page, pageSize, sortField).get();
        return Optional.of(new ListWrapperResponse<>(
                shifts.stream()
                        .map(shift -> new ShiftResponse(shift.get_id().toString(), shift.getShiftName(),
                                DateFormat.toDateString(shift.getStartHour(), DateTime.YYYY_MM_DD),
                                DateFormat.toDateString(shift.getEndHour(), DateTime.YYYY_MM_DD)))
                        .collect(Collectors.toList()),
                page, pageSize, repository.getTotalPage(allParams)));
    }

    @Override
    public Optional<ShiftResponse> getShiftById(String shiftId) {
        Shift shift = shiftInventory.findShiftById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.SHIFT_NOT_FOUND));
        return Optional
                .of(new ShiftResponse(shift.get_id().toString(), shift.getShiftName(),
                        DateFormat.toDateString(shift.getStartHour(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN),
                        DateFormat.toDateString(shift.getEndHour(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN))
                );
    }

    @Override
    public Optional<ShiftResponse> searchShiftByName(String shiftName) {
        Shift shift = shiftInventory.findShiftByName(shiftName)
            .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.SHIFT_NOT_FOUND));
        return Optional
                .of(new ShiftResponse(shift.get_id().toString(), shift.getShiftName(),
                        DateFormat.toDateString(shift.getStartHour(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN),
                        DateFormat.toDateString(shift.getEndHour(), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN))
                );
    }

    @Override
    public void updateShift(ShiftRequest shiftRequest, String shiftId) {
        validate(shiftRequest);
        Shift shift = shiftInventory.findShiftById(shiftId).orElseThrow(
                () -> new ResourceNotFoundException(LanguageMessageKey.SHIFT_NOT_FOUND));
        String normalizeName = StringUtils.normalizeString(shiftRequest.getShiftName());
        shift.setShiftName(normalizeName);
        String startCombine = DateFormat.combineHourAndDate(shiftRequest.getStartDate(), shiftRequest.getStartHour());
        String endCombine = DateFormat.combineHourAndDate(shiftRequest.getEndDate(), shiftRequest.getEndHour());
        shift.setStartHour(DateFormat.convertStringToDate(startCombine, DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN));
        shift.setEndHour(DateFormat.convertStringToDate(endCombine, DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN));
        repository.insertAndUpdate(shift);
    }

    @Override
    public void deleteShift(String shiftId) {
        shiftInventory.findShiftById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.SHIFT_NOT_FOUND));
        repository.deleteShift(shiftId);
    }
}
