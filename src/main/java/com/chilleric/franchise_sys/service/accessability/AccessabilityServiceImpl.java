package com.chilleric.franchise_sys.service.accessability;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityRequest;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.system_repository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.system_repository.accessability.AccessabilityRepository;
import com.chilleric.franchise_sys.repository.system_repository.user.UserRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessabilityServiceImpl
  extends AbstractService<AccessabilityRepository>
  implements AccessabilityService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public Optional<List<AccessabilityResponse>> getAccessByTargetId(
    String targetId,
    String loginId
  ) {
    List<Accessability> accessability = repository
      .getListByAttribute(targetId, "targetId")
      .orElseThrow(
        () -> new ResourceNotFoundException(LanguageMessageKey.ACCESS_NOT_FOUND)
      );
    List<AccessabilityResponse> result = new ArrayList<>();
    accessability.forEach(
      thisAccess -> {
        userRepository
          .getEntityByAttribute(thisAccess.getUserId().toString(), "_id")
          .ifPresent(
            thisUser -> {
              if (thisUser.get_id().toString().compareTo(loginId) != 0) {
                result.add(
                  new AccessabilityResponse(
                    thisAccess.get_id().toString(),
                    thisUser.getFirstName(),
                    thisUser.getUsername(),
                    thisAccess.isEditable()
                  )
                );
              }
            }
          );
      }
    );
    return Optional.of(result);
  }

  @Override
  public void shareAccess(
    AccessabilityRequest accessabilityRequest,
    String loginId,
    String targetId,
    boolean isServer
  ) {
    validate(accessabilityRequest);
    repository
      .getEntityByAttribute(targetId, "targetId")
      .orElseThrow(
        () -> new ResourceNotFoundException(LanguageMessageKey.ACCESS_NOT_FOUND)
      );
    accessabilityRequest
      .getUserIds()
      .forEach(
        thisUserId -> {
          userRepository
            .getEntityByAttribute(thisUserId, "_id")
            .ifPresent(
              thisUser -> {
                if (thisUser.get_id().toString().compareTo(loginId) != 0) {
                  if (repository.getEntityByAttribute(thisUserId, targetId).isEmpty()) {
                    if (accessabilityRequest.getEditable() == 0) {
                      repository.insertAndUpdate(
                        new Accessability(
                          null,
                          thisUser.get_id(),
                          new ObjectId(targetId),
                          false,
                          isServer
                        )
                      );
                    }
                    if (accessabilityRequest.getEditable() == 1) {
                      repository.insertAndUpdate(
                        new Accessability(
                          null,
                          thisUser.get_id(),
                          new ObjectId(targetId),
                          true,
                          isServer
                        )
                      );
                    }
                  }
                }
              }
            );
        }
      );
  }
}
