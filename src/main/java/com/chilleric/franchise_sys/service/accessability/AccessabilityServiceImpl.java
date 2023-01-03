package com.chilleric.franchise_sys.service.accessability;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityRequest;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.accessability.AccessabilityInventory;
import com.chilleric.franchise_sys.inventory.user.UserInventory;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.AccessabilityRepository;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class AccessabilityServiceImpl extends AbstractService<AccessabilityRepository>
        implements AccessabilityService {


    @Autowired
    private AccessabilityInventory accessabilityInventory;

    @Autowired
    private UserInventory userInventory;

    @Override
    public Optional<List<AccessabilityResponse>> getAccessByTargetId(String targetId,
            String loginId) {
        List<Accessability> accessability =
                accessabilityInventory.getAccessByTargetId(targetId).orElseThrow(
                        () -> new ResourceNotFoundException(LanguageMessageKey.ACCESS_NOT_FOUND));
        List<AccessabilityResponse> result = new ArrayList<>();
        accessability.forEach(thisAccess -> {
            userInventory.getActiveUserById(thisAccess.getUserId().toString())
                    .ifPresent(thisUser -> {
                        if (thisUser.get_id().toString().compareTo(loginId) != 0) {
                            result.add(new AccessabilityResponse(thisAccess.get_id().toString(),
                                    thisUser.getFirstName(), thisUser.getUsername(),
                                    thisAccess.isEditable()));
                        }
                    });
        });
        return Optional.of(result);
    }

    @Override
    public void shareAccess(AccessabilityRequest accessabilityRequest, String loginId,
            String targetId) {
        validate(accessabilityRequest);
        accessabilityRequest.getUserIds().forEach(thisUserId -> {
            userInventory.getActiveUserById(thisUserId).ifPresent(thisUser -> {
                if (thisUser.get_id().toString().compareTo(loginId) != 0) {
                    if (accessabilityRequest.getEditable() == 0) {
                        repository.addNewAccessability(new Accessability(null, thisUser.get_id(),
                                new ObjectId(targetId), false));
                    }
                    if (accessabilityRequest.getEditable() == 1) {
                        repository.addNewAccessability(new Accessability(null, thisUser.get_id(),
                                new ObjectId(targetId), true));
                    }
                }
            });
        });

    }

}
